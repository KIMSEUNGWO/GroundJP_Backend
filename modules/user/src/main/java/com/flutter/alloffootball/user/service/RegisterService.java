package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.user.api.SocialProfile;
import com.flutter.alloffootball.common.component.ResponseToken;
import com.flutter.alloffootball.user.dto.login.RegisterRequest;

public interface RegisterService {
    ResponseToken register(RegisterRequest registerRequest, SocialProfile profile);
}
