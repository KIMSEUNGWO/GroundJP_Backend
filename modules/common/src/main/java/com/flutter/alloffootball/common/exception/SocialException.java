package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class SocialException extends CustomRuntimeException {

    private final SocialError error;

    public SocialException(SocialError error) {
        super(error);
        this.error = error;
    }
}
