package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaUserCouponRepository extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findAllByUser_IdAndCouponUseAndExpireDateAfter(Long userId, char couponUse, LocalDateTime now);
}
