package com.sirius.spurt.store.repository.resttemplate.auth.impl;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.ResultCode;
import com.sirius.spurt.store.repository.resttemplate.auth.AuthRepository;
import com.sirius.spurt.store.repository.resttemplate.auth.playload.UserInfoPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AuthRepositoryImpl implements AuthRepository {
    @Value("${user-info-endpoint}")
    private String userInfoEndpoint;

    @Override
    public UserInfoPayload getUserInfo(String accessToken) {
        try {
            MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();

            HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(httpBody);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<UserInfoPayload> res =
                    restTemplate.exchange(
                            userInfoEndpoint + "?access_token=" + accessToken,
                            HttpMethod.GET,
                            req,
                            UserInfoPayload.class);

            return res.getBody();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GlobalException(ResultCode.AUTHENTICATION_FAILED);
        }
    }
}
