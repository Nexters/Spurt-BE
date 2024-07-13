package com.sirius.spurt.store.repository.redis.auth;

public interface AuthRepository {
    void setRefreshToken(final String key, final String value, final long expireTime);

    String getRefreshToken(final String key);

    Boolean hasRefreshToken(final String key);

    void deleteRefreshToken(final String key);
}
