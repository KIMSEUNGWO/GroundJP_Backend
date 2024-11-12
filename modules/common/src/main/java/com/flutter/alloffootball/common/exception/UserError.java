package com.flutter.alloffootball.common.exception;

public enum UserError implements ErrorCode {
    USER_NOT_EXISTS;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
