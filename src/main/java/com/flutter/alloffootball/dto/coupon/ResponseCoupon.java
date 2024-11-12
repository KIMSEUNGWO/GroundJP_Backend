package com.flutter.alloffootball.dto.coupon;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseCoupon {

    private final long couponId;
    private final String title;
    private final int per;
    private final LocalDateTime expireDate;

    public ResponseCoupon(UserCoupon userCoupon) {
        couponId = userCoupon.getId();
        title = userCoupon.getCoupon().getTitle();
        per = userCoupon.getCoupon().getDiscountPer();
        expireDate = userCoupon.getExpireDate();
    }
}
