package com.sirius.spurt.store.repository.redis.token.impl;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void refreshToken_조회_테스트() {
        // given
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(anyString())).thenReturn(TEST_TOKEN_VALUE);

        // when
        String value = tokenRepository.getRefreshToken(TEST_TOKEN_KEY);

        // then
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get(anyString());
        assertThat(value).isEqualTo(TEST_TOKEN_VALUE);
    }

    @Test
    void refreshToken_존재여부_확인_테스트() {
        // given
        when(redisTemplate.hasKey(anyString())).thenReturn(TRUE);

        // when
        Boolean hasRefreshToken = tokenRepository.hasRefreshToken(TEST_TOKEN_KEY);

        // then
        verify(redisTemplate).hasKey(anyString());
        assertThat(hasRefreshToken).isEqualTo(TRUE);
    }

    @Test
    void refreshToken_삭제_테스트() {
        // given

        // when
        tokenRepository.deleteRedisToken(TEST_TOKEN_KEY);

        // then
        verify(redisTemplate).delete(anyString());
    }
}
