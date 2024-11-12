package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class TokenException extends CustomRuntimeException {

    private final TokenError error;

    public TokenException(TokenError error) {
        super(error);
        this.error = error;
    }
}
