package com.flutter.alloffootball.user.component;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.user.dto.coupon.ResponseCouponUse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderCalculator {

    private final int finalPrice;
    private final ResponseCouponUse responseCouponUse;
    private final UserCoupon userCoupon;
}
