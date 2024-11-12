package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class BoardException extends CustomRuntimeException{

    private final BoardError error;

    public BoardException(BoardError error) {
        super(error);
        this.error = error;
    }
}
