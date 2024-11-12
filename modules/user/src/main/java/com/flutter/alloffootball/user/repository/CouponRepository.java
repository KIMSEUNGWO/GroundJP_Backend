package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository {
    List<UserCoupon> findAllByNotUseCoupon(Long userId);

    void refundCoupon(UserCoupon userCoupon, LocalDateTime now);
}
