package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.user.dto.coupon.ResponseCoupon;

import java.util.List;

public interface CouponService {
    List<ResponseCoupon> findAllByCouponsOnlyNotUse(Long userId);
}
