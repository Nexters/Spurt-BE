package com.sirius.spurt.store.provider.auth.impl;

import com.sirius.spurt.store.provider.auth.AuthProvider;
import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import com.sirius.spurt.store.repository.redis.auth.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProviderImpl implements AuthProvider {
    private final AuthRepository authRepository;

    @Override
    public void setRefreshToken(String key, String value, long expireTime) {
        authRepository.setRefreshToken(key, value, expireTime);
    }

    @Override
    public AuthVo getRefreshToken(final String key) {
        return AuthVo.builder().userId(authRepository.getRefreshToken(key)).build();
    }

    @Override
    public Boolean hasRefreshToken(String key) {
        return authRepository.hasRefreshToken(key);
    }

    @Override
    public void deleteRefreshToken(String key) {
        authRepository.deleteRefreshToken(key);
    }
}
