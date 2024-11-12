package com.flutter.alloffootball.mock;

import com.flutter.alloffootball.common.domain.coupon.Coupon;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.jparepository.JpaUserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MockUserCoupon {

    private final JpaUserCouponRepository jpaUserCouponRepository;

    @Autowired
    public MockUserCoupon(JpaUserCouponRepository jpaUserCouponRepository) {
        this.jpaUserCouponRepository = jpaUserCouponRepository;
    }

    public UserCoupon mockUserCoupon(Coupon coupon, User user, char isUse, LocalDateTime expireDate) {
        UserCoupon saveUserCoupon = UserCoupon.builder()
            .coupon(coupon)
            .user(user)
            .couponUse(isUse)
            .expireDate(expireDate)
            .build();
        return jpaUserCouponRepository.save(saveUserCoupon);
    }
}
