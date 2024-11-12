package com.flutter.alloffootball.component;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.dto.coupon.ResponseCouponUse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderCalculator {

    private final int finalPrice;
    private final ResponseCouponUse responseCouponUse;
    private final UserCoupon userCoupon;
}
