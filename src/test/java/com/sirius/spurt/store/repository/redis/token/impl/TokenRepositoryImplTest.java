package com.sirius.spurt.store.repository.redis.token.impl;

import static java.lang.Boolean.FALSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sirius.spurt.test.RefreshTokenTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
class TokenRepositoryImplTest implements RefreshTokenTest {
    @InjectMocks private TokenRepositoryImpl tokenRepository;

    @Mock private RedisTemplate<String, String> redisTemplate;
    @Mock private ValueOperations<String, String> valueOperations;

    @Test
    void refreshToken_저장_테스트() {
        // given
        when(redisTemplate.hasKey(anyString())).thenReturn(FALSE);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // when
        tokenRepository.setRefreshToken(TEST_TOKEN_KEY, TEST_TOKEN_VALUE, TEST_TOKEN_EXPIRE_TIME);

        // then
        verify(redisTemplate).hasKey(anyString());
        verify(redisTemplate, times(0)).delete(anyString());
        verify(redisTemplate).opsForValue();
        verify(valueOperations).set(anyString(), anyString(), any());
    }
}
