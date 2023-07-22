package com.sirius.spurt.store.repository.resttemplate.auth;

import com.sirius.spurt.store.repository.resttemplate.auth.playload.UserInfoPayload;

public interface AuthRepository {
    UserInfoPayload getUserInfo(String accessToken);
}
