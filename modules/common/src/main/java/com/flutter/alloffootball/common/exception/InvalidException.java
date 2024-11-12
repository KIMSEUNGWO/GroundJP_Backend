package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class InvalidException extends CustomRuntimeException {

    private final DefaultError error;

    public InvalidException(DefaultError error) {
        super(error);
        this.error = error;
    }
}
