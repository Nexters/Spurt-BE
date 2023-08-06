package com.sirius.spurt.store.provider.auth;

import com.sirius.spurt.store.provider.auth.vo.AuthVo;

public interface AuthProvider {
    AuthVo getUserId(String accessToken);
}
