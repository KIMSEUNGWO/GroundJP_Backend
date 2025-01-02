package com.flutter.alloffootball.user.api.apk;

import com.flutter.alloffootball.user.api.SocialProfile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public interface SocialAPI {

    SocialProfile getProfile(String accessToken);

    default HttpEntity<String> setAuthorizationHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));
        return new HttpEntity<>(headers);
    }
}
