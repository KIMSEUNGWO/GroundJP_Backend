package com.flutter.alloffootball.service;

import com.flutter.alloffootball.api.SocialProfile;
import com.flutter.alloffootball.common.component.ResponseToken;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.dto.login.RegisterRequest;

public interface RegisterService {
    ResponseToken register(RegisterRequest registerRequest, SocialProfile profile);
}
