package com.sirius.spurt.store.provider.auth.impl;

import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import com.sirius.spurt.store.repository.redis.auth.AuthRepository;
import com.sirius.spurt.test.TokenTest;
import com.sirius.spurt.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthProviderImplTest implements TokenTest, UserTest {
    @InjectMocks private AuthProviderImpl authProvider;

    @Mock private AuthRepository authRepository;

    @Test
    void refreshToken_저장_테스트() {
        // given

        // when
        authProvider.setRefreshToken(TEST_TOKEN_KEY, TEST_TOKEN_VALUE, TEST_TOKEN_EXPIRE_TIME);

        // then
        verify(authRepository).setRefreshToken(anyString(), anyString(), anyLong());
    }

    @Test
    void refreshToken_조회_테스트() {
        // given
        when(authRepository.getRefreshToken(anyString())).thenReturn(TEST_USER_ID);

        // when
        AuthVo authVo = authProvider.getRefreshToken(TEST_TOKEN_KEY);

        // then
        verify(authRepository).getRefreshToken(anyString());
        assertThat(authVo.getUserId()).isEqualTo(TEST_USER_ID);
    }

    @Test
    void refreshToken_존재여부_확인_테스트() {
        // given
        when(authRepository.hasRefreshToken(anyString())).thenReturn(TRUE);

        // when
        Boolean hasRefreshToken = authProvider.hasRefreshToken(TEST_TOKEN_KEY);

        // then
        verify(authRepository).hasRefreshToken(anyString());
        assertThat(hasRefreshToken).isEqualTo(TRUE);
    }

    @Test
    void refreshToken_삭제_테스트() {
        // given

        // when
        authProvider.deleteRefreshToken(TEST_TOKEN_KEY);

        // then
        verify(authRepository).deleteRefreshToken(anyString());
    }
}
