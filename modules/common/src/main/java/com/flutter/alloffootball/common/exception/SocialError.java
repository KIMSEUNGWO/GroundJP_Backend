package com.flutter.alloffootball.common.exception;

public enum SocialError implements ErrorCode {

    INVALID_CLIENT_ID,
    INVALID_REQUEST;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
