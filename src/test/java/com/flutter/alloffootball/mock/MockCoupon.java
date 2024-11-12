package com.flutter.alloffootball.mock;

import com.flutter.alloffootball.common.domain.coupon.Coupon;
import com.flutter.alloffootball.common.jparepository.JpaCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockCoupon {

    private final JpaCouponRepository jpaCouponRepository;

    @Autowired
    public MockCoupon(JpaCouponRepository jpaCouponRepository) {
        this.jpaCouponRepository = jpaCouponRepository;
    }

    public Coupon mockCoupon(int discountPer, int expireDays) {
        Coupon saveCoupon = Coupon.builder()
            .title("신규회원 50% 할인쿠폰")
            .discountPer(discountPer)
            .expireDay(expireDays)
            .build();
        return jpaCouponRepository.save(saveCoupon);
    }
}
