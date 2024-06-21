package com.sirius.spurt.store.provider.jobgroup.impl;

import static com.sirius.spurt.common.meta.ResultCode.NOT_EXIST_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class 직군_수정 {
        @Test
        void 직군_수정_성공_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(TEST_USER);

            // when
            jobGroupProvider.updateJobGroup(TEST_USER_ID, TEST_EMAIL, TEST_JOB_GROUP);

            // then
            verify(userRepository).findByUserId(any());
            verify(userRepository).save(any());
        }

        @Test
        void 직군_수정_실패_테스트() {
            // given
            when(userRepository.findByUserId(any())).thenReturn(null);

            // when
            GlobalException exception =
                    assertThrows(
                            GlobalException.class,
                            () -> {
                                jobGroupProvider.updateJobGroup(TEST_USER_ID, TEST_EMAIL, TEST_JOB_GROUP);
                            });

            // then
            verify(userRepository).findByUserId(any());
            assertThat(exception.getResultCode()).isEqualTo(NOT_EXIST_USER);
        }
    }
}
