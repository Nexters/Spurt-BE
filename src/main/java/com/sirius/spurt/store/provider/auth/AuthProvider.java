package com.sirius.spurt.store.provider.auth;

public interface AuthProvider {
    String[] getUserId(String accessToken);
}
