package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class FieldException extends CustomRuntimeException {

    private final FieldError error;

    public FieldException(FieldError error) {
        super(error);
        this.error = error;
    }
}
