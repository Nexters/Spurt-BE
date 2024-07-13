package com.sirius.spurt.common.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.ResultCode;
import com.sirius.spurt.service.controller.RestResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (GlobalException e) {
            setErrorResponse(response, e.getResultCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ResultCode resultCode)
            throws IOException {
        response.setCharacterEncoding(UTF_8.name());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(SC_OK);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(RestResponse.error(resultCode)));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            response.getWriter().close();
        }
    }
}
