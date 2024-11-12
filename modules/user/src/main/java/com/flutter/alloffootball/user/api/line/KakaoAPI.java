package com.flutter.alloffootball.user.api.line;

import com.flutter.alloffootball.user.api.ResponseTo;
import com.flutter.alloffootball.user.api.SocialProfile;
import com.flutter.alloffootball.common.exception.SocialError;
import com.flutter.alloffootball.common.exception.SocialException;
import com.flutter.alloffootball.user.dto.social.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class KakaoAPI {

    private final ResponseTo responseTo;

    private final String profileURI = "https://kapi.kakao.com/v2/user/me";

    public SocialProfile getProfile(String accessToken) {
        try {
            KakaoProfile profile = responseTo.getProfile(profileURI, KakaoProfile.class, generateAuthorizationEntity(accessToken));
            return SocialProfile.kakao(profile);
        } catch (HttpClientErrorException e) {
            throw new SocialException(SocialError.INVALID_REQUEST);
        }
    }

    private HttpEntity<String> generateAuthorizationEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));
        return new HttpEntity<>(headers);
    }
}
