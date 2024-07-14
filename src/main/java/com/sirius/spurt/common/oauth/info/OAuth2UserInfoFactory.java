package com.sirius.spurt.common.oauth.info;

import static com.sirius.spurt.common.meta.ResultCode.UNKNOWN_SOCIAL;
import static com.sirius.spurt.common.meta.Social.GOOGLE;

import com.sirius.spurt.common.exception.GlobalException;
import com.sirius.spurt.common.meta.Social;
import com.sirius.spurt.common.oauth.info.impl.GoogleOAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(Social social, Map<String, Object> attributes) {
        if (GOOGLE.equals(social)) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        throw new GlobalException(UNKNOWN_SOCIAL);
    }
}
