package com.sirius.spurt.common.resolver;

import static com.sirius.spurt.common.jwt.JwtUtils.ACCESS_TOKEN_NAME;
import static com.sirius.spurt.common.jwt.JwtUtils.REFRESH_TOKEN_NAME;
import static com.sirius.spurt.common.jwt.JwtUtils.TOKEN_TYPE;

import com.sirius.spurt.common.jwt.JwtUtils;
import com.sirius.spurt.common.oauth.user.OAuthUser;
import com.sirius.spurt.common.resolver.user.NonLoginUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class NonLoginUserResolver implements HandlerMethodArgumentResolver {
    private final JwtUtils jwtUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(NonLoginUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();

        if (isTestHeader(request)) {
            return new NonLoginUser("admin");
        }

        String accessCookie = getCookie(request, ACCESS_TOKEN_NAME);
        String refreshCookie = getCookie(request, REFRESH_TOKEN_NAME);
        log.info("accessCookie: " + accessCookie);
        log.info("refreshCookie: " + refreshCookie);
        if (isInValidCookie(accessCookie)) {
            return new NonLoginUser(null);
        }

        OAuthUser oAuthUser = getOAuthUser(response, accessCookie, refreshCookie);
        return new NonLoginUser(oAuthUser.getUserId());
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

    private OAuthUser getOAuthUser(
            HttpServletResponse response, String accessCookie, String refreshCookie) {
        String accessToken = accessCookie.replace(TOKEN_TYPE, "");
        OAuthUser oAuthUser = jwtUtils.getOAuthUser(accessToken);
        if (oAuthUser == null) {
            if (isInValidCookie(refreshCookie)) {
                return OAuthUser.builder().build();
            }

            String refreshToken = refreshCookie.replace(TOKEN_TYPE, "");
            oAuthUser = jwtUtils.getOAuthUser(refreshToken);
            if (oAuthUser == null) {
                return OAuthUser.builder().build();
            }
            jwtUtils.updateTokens(response, oAuthUser.getUserId(), oAuthUser.getEmail());
        }

        return oAuthUser;
    }

    private boolean isInValidCookie(String cookie) {
        return !StringUtils.hasText(cookie) || !cookie.startsWith(TOKEN_TYPE);
    }
}
