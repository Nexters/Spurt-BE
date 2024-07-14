package com.sirius.spurt.common.jwt;

import static com.sirius.spurt.common.jwt.JwtUtils.ACCESS_TOKEN_NAME;
import static com.sirius.spurt.common.jwt.JwtUtils.REFRESH_TOKEN_NAME;
import static com.sirius.spurt.common.jwt.JwtUtils.TOKEN_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.common.oauth.user.OAuthUser;
import com.sirius.spurt.common.validator.TokenValidator;
import com.sirius.spurt.common.validator.UserValidator;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    private static final List<RequestMatcher> whiteList =
            List.of(
                    new AntPathRequestMatcher("/oauth2/**", GET.name()),
                    new AntPathRequestMatcher("/v1/question/random", GET.name()),
                    new AntPathRequestMatcher("/v1/jobgroup", POST.name()));

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isTestHeader(request)) {
            setAuthentication("admin");
            filterChain.doFilter(request, response);
            return;
        }

        String accessCookie = getCookie(request, ACCESS_TOKEN_NAME);
        String refreshCookie = getCookie(request, REFRESH_TOKEN_NAME);

        log.info("accessCookie: " + accessCookie);
        log.info("refreshCookie: " + refreshCookie);
        TokenValidator.validateCookie(accessCookie);

        String accessToken = accessCookie.replace(TOKEN_TYPE, "");
        OAuthUser oAuthUser = jwtUtils.getOAuthUser(accessToken);
        if (oAuthUser == null) {
            TokenValidator.validateCookie(refreshCookie);
            String refreshToken = refreshCookie.replace(TOKEN_TYPE, "");
            oAuthUser = jwtUtils.getOAuthUser(refreshToken);
            TokenValidator.validateOAuthUser(oAuthUser);
            jwtUtils.updateTokens(response, oAuthUser.getUserId(), oAuthUser.getEmail());
        }

        setAuthentication(oAuthUser.getUserId());
        filterChain.doFilter(request, response);
    }

    private boolean isTestHeader(HttpServletRequest request) {
        return StringUtils.hasText(request.getHeader("test"));
    }

    private String getCookie(HttpServletRequest request, String key) {
        if (ArrayUtils.isEmpty(request.getCookies())) {
            return null;
        }

        Cookie cookie =
                Arrays.stream(request.getCookies())
                        .filter(c -> key.equals(c.getName()))
                        .findFirst()
                        .orElse(null);
        if (cookie == null) {
            return null;
        }

        return cookie.getValue();
    }

    private void setAuthentication(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);
        PrincipalDetails principalDetails = PrincipalDetails.builder().userEntity(userEntity).build();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return whiteList.stream().anyMatch(url -> url.matches(request));
    }
}
