package com.flutter.alloffootball.service;

import com.flutter.alloffootball.dto.coupon.ResponseCoupon;

import java.util.List;

public interface CouponService {
    List<ResponseCoupon> findAllByCouponsOnlyNotUse(Long userId);
}
