package com.sirius.spurt.store.provider.auth.impl;

import com.sirius.spurt.store.provider.auth.AuthProvider;
import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import com.sirius.spurt.store.repository.resttemplate.auth.AuthRepository;
import com.sirius.spurt.store.repository.resttemplate.auth.playload.UserInfoPayload;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProviderImpl implements AuthProvider {
    private final AuthRepository authRepository;

    @Override
    public AuthVo getUserId(String accessToken) {
        return AuthProviderImplMapper.INSTANCE.toAuthVo(authRepository.getUserInfo(accessToken));
    }

    @Mapper
    public interface AuthProviderImplMapper {
        AuthProviderImplMapper INSTANCE = Mappers.getMapper(AuthProviderImplMapper.class);

        AuthVo toAuthVo(UserInfoPayload userInfoPayload);
    }
}
