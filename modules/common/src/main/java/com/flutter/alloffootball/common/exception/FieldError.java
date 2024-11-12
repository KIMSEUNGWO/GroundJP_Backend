package com.flutter.alloffootball.common.exception;

public enum FieldError implements ErrorCode {

    FIELD_NOT_EXISTS;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
