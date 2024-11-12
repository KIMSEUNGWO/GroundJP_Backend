package com.flutter.alloffootball.common.exception;

public enum MatchError implements ErrorCode{

    MATCH_NOT_EXISTS;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
