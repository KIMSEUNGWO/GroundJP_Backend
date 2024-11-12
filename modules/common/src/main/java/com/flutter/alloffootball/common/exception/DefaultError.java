package com.flutter.alloffootball.common.exception;

public enum DefaultError implements ErrorCode {

    OK,
    REGISTER,
    NOT_AUTHENTICATION,
    INVALID_REQUEST, // 데이터 조작 의심 예외
    INVALID_DATA;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
