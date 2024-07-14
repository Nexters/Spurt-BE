package com.sirius.spurt.common.oauth.handler;

import static com.sirius.spurt.common.meta.ResultCode.AUTHENTICATION_FAILED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.spurt.service.controller.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        setErrorResponse(response, RestResponse.error(AUTHENTICATION_FAILED));
    }

    private void setErrorResponse(HttpServletResponse response, RestResponse<?> res)
            throws IOException {
        response.setCharacterEncoding(UTF_8.name());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(SC_OK);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(res));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            response.getWriter().close();
        }
    }
}
