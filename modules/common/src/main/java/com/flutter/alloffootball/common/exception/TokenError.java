package com.flutter.alloffootball.common.exception;

public enum TokenError implements ErrorCode {

    TOKEN_EXPIRED,
    REFRESH_TOKEN_EXPIRED,
    ACCESS_TOKEN_REQUIRE;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
