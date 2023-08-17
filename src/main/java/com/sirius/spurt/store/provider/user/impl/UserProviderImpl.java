package com.sirius.spurt.store.provider.user.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.provider.user.vo.UserVo;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProviderImpl implements UserProvider {
    private final UserRepository userRepository;

    @Override
    public UserVo getUserInfo(String userId) {
        return UserProviderImplMapper.INSTANCE.toUserVo(userRepository.findByUserId(userId));
    }

    @Override
    public boolean checkUserExists(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public boolean checkHasPined(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new GlobalException(NOT_EXIST_USER);
        }

        return userEntity.getHasPined();
    }

    @Mapper
    public interface UserProviderImplMapper {
        UserProviderImpl.UserProviderImplMapper INSTANCE =
                Mappers.getMapper(UserProviderImpl.UserProviderImplMapper.class);

        UserVo toUserVo(UserEntity entity);
    }
}
