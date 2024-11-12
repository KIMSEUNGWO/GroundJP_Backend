package com.flutter.alloffootball.common.exception;

import lombok.Getter;

@Getter
public class CouponException extends CustomRuntimeException {

    private final CouponError error;

    public CouponException(CouponError error) {
        super(error);
        this.error = error;
    }
}
