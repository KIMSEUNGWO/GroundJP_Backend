package com.flutter.alloffootball.common.exception;

public enum BoardError implements ErrorCode{

    BOARD_NOT_EXISTS;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}