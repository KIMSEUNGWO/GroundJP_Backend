package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class NoticeException extends CustomRuntimeException {

    private final NoticeError error;

    public NoticeException(NoticeError error) {
        super(error);
        this.error = error;
    }
}
