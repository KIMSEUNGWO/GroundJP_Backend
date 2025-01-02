package com.flutter.alloffootball.user.config.jwt;

import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUserContextHolder {

    public static UserJwtToken getUserJwtToken() {
        return (UserJwtToken) SecurityContextHolder.getContext().getAuthentication();
    }
}
