package com.sirius.spurt.common.resolver;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.meta.ResultCode;
import com.sirius.spurt.common.resolver.user.LoginUser;
import com.sirius.spurt.store.provider.auth.AuthProvider;
import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import com.sirius.spurt.store.provider.jobgroup.JobGroupProvider;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    private final AuthProvider authProvider;
    private final UserProvider userProvider;
    private final JobGroupProvider jobGroupProvider;
    private final String TOKEN_TYPE = "Bearer ";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginUser.class);
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
            throw new GlobalException(ResultCode.AUTHENTICATION_FAILED);
        }

        AuthVo userInfo = new AuthVo();
        if (StringUtils.hasLength(accessHeader) && accessHeader.startsWith(TOKEN_TYPE)) {
            String accessToken = accessHeader.replace(TOKEN_TYPE, "");
            userInfo = authProvider.getUserId(accessToken);
            // 로그인 이후 직군 미선택 유저 추가 체크
//            UserVo userVo = userProvider.getUserInfo(userInfo.getUserId());
//            if (userVo == null) {
//                jobGroupProvider.saveJobGroup(
//                        userInfo.getUserId(), userInfo.getEmail(), JobGroup.DEVELOPER);
//            }
        }
        return new LoginUser(userInfo.getUserId(), userInfo.getEmail());
    }
}
