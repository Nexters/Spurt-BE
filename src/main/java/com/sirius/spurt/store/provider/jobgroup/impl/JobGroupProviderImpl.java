package com.sirius.spurt.store.provider.jobgroup.impl;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.validator.UserValidator;
import com.sirius.spurt.store.provider.jobgroup.JobGroupProvider;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobGroupProviderImpl implements JobGroupProvider {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveJobGroup(final String userId, final String email, final JobGroup jobGroup) {
        userRepository.save(
                UserEntity.builder()
                        .userId(userId)
                        .email(email)
                        .jobGroup(jobGroup)
                        .hasPined(false)
                        .hasPosted(false)
                        .build());
    }

    @Override
    @Transactional
    public void updateJobGroup(final String userId, final String email, final JobGroup jobGroup) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);

        userRepository.save(
                UserEntity.builder()
                        .userId(userId)
                        .email(email)
                        .jobGroup(jobGroup)
                        .hasPined(userEntity.getHasPined())
                        .hasPosted(userEntity.getHasPosted())
                        .build());
    }
}
