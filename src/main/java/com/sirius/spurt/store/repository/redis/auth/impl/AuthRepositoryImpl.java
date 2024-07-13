package com.sirius.spurt.store.repository.redis.auth.impl;

import com.sirius.spurt.store.repository.redis.auth.AuthRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void setRefreshToken(final String key, final String value, final long expireTime) {
        if (hasRefreshToken(key)) {
            deleteRefreshToken(key);
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
    public void deleteRefreshToken(final String key) {
        redisTemplate.delete(key);
    }
}
