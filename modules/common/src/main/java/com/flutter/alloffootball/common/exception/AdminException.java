package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class AdminException extends CustomRuntimeException {

    private final AdminError error;

    public AdminException(AdminError error) {
        super(error);
        this.error = error;
    }
}
