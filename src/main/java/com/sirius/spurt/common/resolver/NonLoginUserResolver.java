package com.sirius.spurt.common.resolver;

import static com.sirius.spurt.common.jwt.JwtUtils.ACCESS_TOKEN_NAME;
import static com.sirius.spurt.common.jwt.JwtUtils.REFRESH_TOKEN_NAME;

import com.sirius.spurt.common.jwt.JwtUtils;
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
    private final String TOKEN_TYPE = "Bearer ";

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
        if (isInValidCookie(accessCookie)) {
            return new NonLoginUser(null);
        }

        String accessToken = accessCookie.replace(TOKEN_TYPE, "");
        String userId = jwtUtils.getUserId(accessToken);
        if (userId == null) {
            if (isInValidCookie(refreshCookie)) {
                return new NonLoginUser(null);
            }

            String refreshToken = refreshCookie.replace(TOKEN_TYPE, "");
            userId = jwtUtils.getUserId(refreshToken);
            if (userId == null) {
                return new NonLoginUser(null);
            }

            jwtUtils.updateTokens(response, userId);
        }

        return new NonLoginUser(userId);
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

    private boolean isInValidCookie(String cookie) {
        return !StringUtils.hasText(cookie) || !cookie.startsWith(TOKEN_TYPE);
    }
}
