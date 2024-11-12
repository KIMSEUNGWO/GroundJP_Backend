package com.flutter.alloffootball.common.exception;

public enum AdminError implements ErrorCode {
    LOGIN_FAILED;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
