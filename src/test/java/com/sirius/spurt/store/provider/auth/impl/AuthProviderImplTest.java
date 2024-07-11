package com.sirius.spurt.store.provider.auth.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import com.sirius.spurt.store.repository.resttemplate.auth.AuthRepository;
import com.sirius.spurt.store.repository.resttemplate.auth.playload.UserInfoPayload;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthProviderImplTest implements UserTest {
    @InjectMocks private AuthProviderImpl authProvider;

    @Mock private AuthRepository authRepository;

    @Test
    void 유저_정보_조회_테스트() {
        // given
        UserInfoPayload userInfo =
                UserInfoPayload.builder().userId(TEST_USER_ID).email(TEST_EMAIL).build();
        when(authRepository.getUserInfo(any())).thenReturn(userInfo);

        // when
        AuthVo authVo = authProvider.getUserId("accessToken");

        // then
        verify(authRepository).getUserInfo(any());
        assertThat(authVo.getUserId()).isEqualTo(userInfo.getUserId());
        assertThat(authVo.getEmail()).isEqualTo(userInfo.getEmail());
    }
}
