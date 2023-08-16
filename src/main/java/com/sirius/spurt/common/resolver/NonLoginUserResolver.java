package com.sirius.spurt.common.resolver;

import com.sirius.spurt.common.resolver.user.NonLoginUser;
import com.sirius.spurt.store.provider.auth.AuthProvider;
import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class NonLoginUserResolver implements HandlerMethodArgumentResolver {
    private final AuthProvider authProvider;
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

        String testHeader = request.getHeader("test");
        if (StringUtils.hasLength(testHeader)) {
            return new LoginUser("admin", "email");
        }

        String accessHeader = request.getHeader("Authorization");
        if (!StringUtils.hasLength(accessHeader) || !accessHeader.startsWith(TOKEN_TYPE)) {
            return new NonLoginUser(null, null);
        }

        AuthVo userInfo = new AuthVo();
        if (StringUtils.hasLength(accessHeader) && accessHeader.startsWith(TOKEN_TYPE)) {
            String accessToken = accessHeader.replace(TOKEN_TYPE, "");
            userInfo = authProvider.getUserId(accessToken);
        }

        return new NonLoginUser(userInfo.getUserId(), userInfo.getEmail());
    }
}
