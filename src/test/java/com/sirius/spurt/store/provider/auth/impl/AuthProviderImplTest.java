package com.sirius.spurt.store.provider.auth.impl;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import com.sirius.spurt.store.repository.redis.auth.AuthRepository;
import com.sirius.spurt.test.TokenTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthProviderImplTest implements TokenTest {
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
}
