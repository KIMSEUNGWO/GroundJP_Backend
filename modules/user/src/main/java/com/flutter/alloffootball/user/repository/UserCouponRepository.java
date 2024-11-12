package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.user.dto.coupon.ResponseCouponUse;

import java.time.LocalDateTime;

public interface UserCouponRepository {
    UserCoupon findById(Long couponId);

    ResponseCouponUse useCoupon(UserCoupon userCoupon, long userId, LocalDateTime now, int totalPrice);
}
