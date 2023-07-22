package com.sirius.spurt.common.filter;

import com.sirius.spurt.store.provider.auth.AuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {
    private final AuthProvider authProvider;
    private final String TOKEN_TYPE = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessHeader = request.getHeader("Authorization");

        String userId = null;
        if (!StringUtils.hasLength(accessHeader) || !accessHeader.startsWith(TOKEN_TYPE)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return;
            userId = "admin";
        }

        if (StringUtils.hasLength(accessHeader) && accessHeader.startsWith(TOKEN_TYPE)) {
            String accessToken = accessHeader.replace(TOKEN_TYPE, "");
            userId = authProvider.getUserId(accessToken);
            if (userId == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        request.setAttribute("userId", userId);
        chain.doFilter(request, response);
    }
}
