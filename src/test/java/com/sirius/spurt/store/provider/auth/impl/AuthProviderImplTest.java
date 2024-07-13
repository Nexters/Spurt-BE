package com.sirius.spurt.store.provider.auth.impl;

import com.sirius.spurt.store.repository.redis.auth.AuthRepository;
import com.sirius.spurt.test.RefreshTokenTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthProviderImplTest implements RefreshTokenTest {
    @InjectMocks private AuthProviderImpl authProvider;

    @Mock private AuthRepository authRepository;
}
