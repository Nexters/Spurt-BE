package com.sirius.spurt.common.oauth.service;

import static com.sirius.spurt.common.meta.ResultCode.SYSTEM_ERROR;

import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.Social;
import com.sirius.spurt.common.oauth.info.OAuth2UserInfo;
import com.sirius.spurt.common.oauth.info.OAuth2UserInfoFactory;
import com.sirius.spurt.store.repository.database.entity.UserEntity;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return process(oAuth2UserRequest, oAuth2User);
        } catch (Exception e) {
            throw new GlobalException(SYSTEM_ERROR);
        }
    }

    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Social social =
                Social.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo =
                OAuth2UserInfoFactory.getOAuth2UserInfo(social, oAuth2User.getAttributes());
        UserEntity userEntity = getUserByUserId(oAuth2UserInfo.getUserId(), oAuth2UserInfo.getEmail());
        return PrincipalDetails.builder().userEntity(userEntity).build();
    }

    private UserEntity getUserByUserId(String userId, String email) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            return UserEntity.builder().userId(userId).email(email).build();
        }
        return userEntity;
    }
}
