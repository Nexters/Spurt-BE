package com.sirius.spurt.store.provider.user.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.store.provider.user.vo.UserVo;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserProviderImplTest implements UserTest {
    @InjectMocks private UserProviderImpl userProvider;

    @Mock private UserRepository userRepository;

    @Test
    void 유저_정보_조회_테스트() {
        // given
        when(userRepository.findByUserId(any())).thenReturn(TEST_USER);

        // when
        UserVo userVo = userProvider.getUserInfo(TEST_USER_ID);

        // then
        verify(userRepository).findByUserId(any());
        assertThat(userVo.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(userVo.getJobGroup()).isEqualTo(TEST_JOB_GROUP);
    }
}
