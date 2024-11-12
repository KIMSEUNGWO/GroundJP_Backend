package com.flutter.alloffootball.common.exception;

public enum CouponError implements ErrorCode {
    COUPON_NOT_EXISTS, // 쿠폰이 없는 경우
    COUPON_ALREADY_USE, // 쿠폰이 이미 사용된 경우
    COUPON_EXPIRE // 쿠폰이 만료된 경우
    ;

    @Override
    public String getErrorCode() {
        return this.name();
    }
}
