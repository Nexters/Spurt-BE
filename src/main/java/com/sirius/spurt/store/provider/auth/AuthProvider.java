package com.sirius.spurt.store.provider.auth;

import com.sirius.spurt.store.provider.auth.vo.AuthVo;

public interface AuthProvider {
    void setRefreshToken(final String key, final String value, final long expireTime);

    AuthVo getRefreshToken(final String key);

    Boolean hasRefreshToken(final String key);

    void deleteRefreshToken(final String key);
}
