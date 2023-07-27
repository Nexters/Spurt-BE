package com.sirius.spurt.store.provider.user.impl;

import com.sirius.spurt.store.provider.user.UserProvider;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProviderImpl implements UserProvider {
    private final UserRepository userRepository;

    @Override
    public boolean checkUserExists(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
