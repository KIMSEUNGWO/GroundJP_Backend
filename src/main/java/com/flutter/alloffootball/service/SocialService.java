package com.flutter.alloffootball.service;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.dto.login.SocialLoginDto;

import java.util.Optional;

public interface SocialService {
    Optional<User> socialLogin(SocialLoginDto loginDto);

    User getUserInfoByUsingRefreshToken(String refreshToken);
}
