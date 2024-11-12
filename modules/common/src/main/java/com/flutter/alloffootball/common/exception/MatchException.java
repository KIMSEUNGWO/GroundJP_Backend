package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class MatchException extends CustomRuntimeException {

    private final MatchError error;

    public MatchException(MatchError error) {
        super(error);
        this.error = error;
    }
}
