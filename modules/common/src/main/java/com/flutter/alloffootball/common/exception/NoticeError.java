package com.flutter.alloffootball.common.exception;

public enum NoticeError implements ErrorCode {

    NOTICE_NOT_EXISTS;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
