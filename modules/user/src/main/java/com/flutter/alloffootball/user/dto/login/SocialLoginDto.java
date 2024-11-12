package com.flutter.alloffootball.user.dto.login;

import com.flutter.alloffootball.common.enums.Provider;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SocialLoginDto {

    @NotNull
    private String socialId;
    @NotNull
    private Provider provider;
    @NotNull
    private String accessToken;
}
