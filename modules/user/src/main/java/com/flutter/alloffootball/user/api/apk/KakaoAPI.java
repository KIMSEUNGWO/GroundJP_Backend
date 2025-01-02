package com.flutter.alloffootball.user.api.apk;

import com.flutter.alloffootball.user.api.ResponseTo;
import com.flutter.alloffootball.user.api.SocialProfile;
import com.flutter.alloffootball.common.exception.SocialError;
import com.flutter.alloffootball.common.exception.SocialException;
import com.flutter.alloffootball.user.dto.social.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class KakaoAPI implements SocialAPI {

    private final ResponseTo responseTo;

    private final String profileURI = "https://kapi.kakao.com/v2/user/me";

    @Override
    public SocialProfile getProfile(String accessToken) {
        try {
            KakaoProfile profile = responseTo.getProfile(profileURI, KakaoProfile.class, setAuthorizationHeader(accessToken));
            return SocialProfile.kakao(profile);
        } catch (HttpClientErrorException e) {
            throw new SocialException(SocialError.INVALID_REQUEST);
        }
    }

}
