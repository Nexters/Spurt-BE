package com.sirius.spurt.store.repository.redis.token.impl;

import com.sirius.spurt.store.repository.redis.token.TokenRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void setRefreshToken(final String key, final String value, final long expireTime) {
        if (hasRefreshToken(key)) {
            deleteRedisToken(key);
        }

        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expireTime));
    }

    @Override
    public String getRefreshToken(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean hasRefreshToken(final String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void deleteRedisToken(final String key) {
        redisTemplate.delete(key);
    }
}
