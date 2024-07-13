package com.sirius.spurt.store.repository.redis.token;

public interface TokenRepository {
    void setRefreshToken(final String key, final String value, final long expireTime);

    String getRefreshToken(final String key);

    Boolean hasRefreshToken(final String key);

    void deleteRedisToken(final String key);
}
