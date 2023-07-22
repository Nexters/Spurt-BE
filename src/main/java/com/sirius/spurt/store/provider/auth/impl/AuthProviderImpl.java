package com.sirius.spurt.store.provider.auth.impl;

import com.sirius.spurt.store.provider.auth.AuthProvider;
import com.sirius.spurt.store.repository.resttemplate.auth.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProviderImpl implements AuthProvider {
    private final AuthRepository authRepository;

    @Override
    public String getUserId(String accessToken) {
        return authRepository.getUserInfo(accessToken).getUserId();
    }
}
