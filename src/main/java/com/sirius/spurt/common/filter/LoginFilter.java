package com.sirius.spurt.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.spurt.common.meta.ResultCode;
import com.sirius.spurt.service.controller.RestResponse;
import com.sirius.spurt.store.provider.auth.AuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {
    private final AuthProvider authProvider;
    private final String TOKEN_TYPE = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessHeader = request.getHeader("Authorization");

        if (!StringUtils.hasLength(accessHeader) || !accessHeader.startsWith(TOKEN_TYPE)) {
            setErrorResponse(response, ResultCode.AUTHENTICATION_FAILED);
            return;
        }

        String userId = "admin";
        if (StringUtils.hasLength(accessHeader) && accessHeader.startsWith(TOKEN_TYPE)) {
            String accessToken = accessHeader.replace(TOKEN_TYPE, "");
            try {
                userId = authProvider.getUserId(accessToken);
            } catch (Exception e) {
                setErrorResponse(response, ResultCode.AUTHENTICATION_FAILED);
                return;
            }
        }

        request.setAttribute("userId", userId);
        chain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ResultCode resultCode)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(RestResponse.error(resultCode)));
    }
}
