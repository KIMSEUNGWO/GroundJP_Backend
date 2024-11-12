package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class UserException extends CustomRuntimeException {

    private final UserError error;

    public UserException(UserError error) {
        super(error);
        this.error = error;
    }
}
