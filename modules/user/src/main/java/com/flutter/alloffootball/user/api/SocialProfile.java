package com.flutter.alloffootball.user.api;

import com.flutter.alloffootball.user.dto.social.KakaoProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SocialProfile {

    private String userId;
    private String displayName;

    public static SocialProfile kakao(KakaoProfile profile) {
        SocialProfile social = new SocialProfile();
        social.setUserId(String.valueOf(profile.getId()));
        social.setDisplayName((String) profile.getProperties().get("nickname"));
        return social;
    }
}
