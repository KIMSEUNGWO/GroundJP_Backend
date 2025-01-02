package com.flutter.alloffootball.user.api;

import com.flutter.alloffootball.user.api.apk.KakaoAPI;
import com.flutter.alloffootball.user.api.apk.LineAPI;
import com.flutter.alloffootball.common.enums.Provider;
import com.flutter.alloffootball.common.exception.SocialError;
import com.flutter.alloffootball.common.exception.SocialException;
import com.flutter.alloffootball.user.api.apk.SocialAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocialVerifier {

    private final Map<Provider, SocialAPI> map;

    @Autowired
    public SocialVerifier(LineAPI lineAPI, KakaoAPI kakaoAPI) {
        Map<Provider, SocialAPI> apiMap = new HashMap<>();
        apiMap.put(Provider.LINE, lineAPI);
        apiMap.put(Provider.KAKAO, kakaoAPI);

        this.map = apiMap;
    }

    public SocialProfile getProfile(String socialId, Provider provider, String accessToken) {
        if (socialId == null || provider == null || accessToken == null) {
            throw new SocialException(SocialError.INVALID_REQUEST);
        }
        return map.get(provider).getProfile(accessToken);
//        return switch (provider) {
//            case LINE -> lineAPI.getProfile(accessToken);
//            case KAKAO -> kakaoAPI.getProfile(accessToken);
//            case APPLE -> throw new SocialException(SocialError.INVALID_REQUEST);
//        };
    }
}
