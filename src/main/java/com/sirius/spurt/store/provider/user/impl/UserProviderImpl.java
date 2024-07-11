package com.sirius.spurt.store.provider.user.impl;

import com.sirius.spurt.common.validator.UserValidator;
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
    public UserVo getUserInfo(final String userId) {
        return UserProviderImplMapper.INSTANCE.toUserVo(userRepository.findByUserId(userId));
    }

    @Override
    public boolean checkUserExists(final String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public boolean checkHasPined(final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);
        return userEntity.getHasPined();
    }

    @Override
    public boolean checkHasPosted(final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);
        return userEntity.getHasPosted();
    }

    @Override
    public void deleteUser(final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);
        userRepository.delete(userEntity);
    }

    @Mapper
    public interface UserProviderImplMapper {
        UserProviderImpl.UserProviderImplMapper INSTANCE =
                Mappers.getMapper(UserProviderImpl.UserProviderImplMapper.class);

        UserVo toUserVo(UserEntity entity);
    }
}
