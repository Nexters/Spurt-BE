package com.sirius.spurt.common.auth;

import com.sirius.spurt.common.validator.UserValidator;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validator(userEntity);
        return PrincipalDetails.builder().userEntity(userEntity).build();
    }
}
