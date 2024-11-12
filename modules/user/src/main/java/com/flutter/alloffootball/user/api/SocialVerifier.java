package com.flutter.alloffootball.user.api;

import com.flutter.alloffootball.user.api.line.KakaoAPI;
import com.flutter.alloffootball.user.api.line.LineAPI;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.exception.SocialError;
import com.flutter.alloffootball.common.exception.SocialException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialVerifier {

    private final LineAPI lineAPI;
    private final KakaoAPI kakaoAPI;

    public SocialProfile getProfile(String socialId, Provider provider, String accessToken) {
        if (socialId == null || provider == null || accessToken == null) {
            throw new SocialException(SocialError.INVALID_REQUEST);
        }

        if (provider == Provider.LINE) {
            lineAPI.accessTokenVerify(accessToken);
            return lineAPI.getSocialProfile(accessToken);
        } else if (provider == Provider.KAKAO) {
            return kakaoAPI.getProfile(accessToken);
        }

        throw new SocialException(SocialError.INVALID_REQUEST);
    }
}
