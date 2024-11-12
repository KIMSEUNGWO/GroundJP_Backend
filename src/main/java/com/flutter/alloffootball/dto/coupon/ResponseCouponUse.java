package com.flutter.alloffootball.dto.coupon;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResponseCouponUse {

    private final String title;
    private final int discount;
    private final int totalPrice;
}
