package com.sirius.spurt.common.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sirius.spurt.common.template.Business;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomFilter extends OncePerRequestFilter {
    @Value("${token-info-endpoint}")
    private String tokenInfoEndpoint;

    private final String TOKEN_TYPE = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessHeader = request.getHeader("Authorization");

        if (accessHeader == null || !accessHeader.startsWith(TOKEN_TYPE)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String accessToken = accessHeader.replace(TOKEN_TYPE, "");
        Result tokenInfoResponse = getTokenInfo(accessToken);
        if (tokenInfoResponse == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        chain.doFilter(request, response);
    }

    private Result getTokenInfo(String accessToken) {
        try {
            MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();

            HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(httpBody);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Result> res =
                    restTemplate.exchange(
                            tokenInfoEndpoint + "?access_token=" + accessToken,
                            HttpMethod.GET,
                            req,
                            Result.class);

            if (res.getStatusCode().is2xxSuccessful()) {
                return res.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Setter
    @Getter
    @JsonIgnoreProperties
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result implements Business.Result, Serializable {
        private String issued_to;
        private String audience;
        private String user_id;
        private String scope;
        private Long expires_in;
        private String email;
        private boolean verified_email;
        private String access_type;
    }
}
