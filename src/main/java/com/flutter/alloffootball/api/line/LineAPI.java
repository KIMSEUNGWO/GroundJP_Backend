package com.flutter.alloffootball.api.line;

import com.flutter.alloffootball.api.ResponseTo;
import com.flutter.alloffootball.api.SocialProfile;
import com.flutter.alloffootball.common.exception.SocialError;
import com.flutter.alloffootball.common.exception.SocialException;
import com.flutter.alloffootball.common.exception.TokenError;
import com.flutter.alloffootball.common.exception.TokenException;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LineAPI {

    private final ResponseTo responseTo;

    @Value("${line.login.channel.id}")
    private String channelId;

    private final String verifyUrl = "https://api.line.me/oauth2/v2.1/verify?access_token=";
    private final String profileURL = "https://api.line.me/v2/profile?access_token=";

    public void accessTokenVerify(String accessToken) {

        try {
            LineResponse response = responseTo.get(verifyUrl + accessToken, LineResponse.class);

            if (!Objects.equals(channelId, response.getClient_id())) {
                throw new SocialException(SocialError.INVALID_CLIENT_ID);
            }

            if (response.getExpires_in() < 1) {
                throw new TokenException(TokenError.TOKEN_EXPIRED);
            }


        } catch (HttpClientErrorException e) {
            throw new SocialException(SocialError.INVALID_REQUEST);
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @ToString
    public static class LineResponse {

        private String scope, client_id;
        private int expires_in;

    }

    public SocialProfile getSocialProfile(String accessToken) {
        return responseTo.getProfile(profileURL + accessToken, SocialProfile.class, generateAuthorizationEntity(accessToken));
    }

    private HttpEntity<String> generateAuthorizationEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));
        return new HttpEntity<>(headers);
    }

}