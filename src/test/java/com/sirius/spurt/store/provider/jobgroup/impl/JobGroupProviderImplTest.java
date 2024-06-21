package com.sirius.spurt.store.provider.jobgroup.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JobGroupProviderImplTest implements UserTest {
    @InjectMocks private JobGroupProviderImpl jobGroupProvider;

    @Mock private UserRepository userRepository;

    @Test
    void 직군_저장_테스트() {
        // given

        // when
        jobGroupProvider.saveJobGroup(TEST_USER_ID, TEST_EMAIL, TEST_JOB_GROUP);

        // then
        verify(userRepository).save(any());
    }
}
