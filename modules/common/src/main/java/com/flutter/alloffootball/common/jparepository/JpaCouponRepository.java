package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends JpaRepository<Coupon, Long> {
}
