package com.sirius.spurt.common.oauth.handler;

import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.common.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;

    private final String IS_COMMITTED = "Response has already committed.";

    @Value("${redirect-uri.prod}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            log.debug(IS_COMMITTED + targetUrl);
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        jwtUtils.setAccessToken(
                response,
                principalDetails.getUserEntity().getUserId(),
                principalDetails.getUserEntity().getEmail());
        jwtUtils.setRefreshToken(
                response,
                principalDetails.getUserEntity().getUserId(),
                principalDetails.getUserEntity().getEmail());
        return UriComponentsBuilder.fromUriString(redirectUri).build().toString();
    }
}
