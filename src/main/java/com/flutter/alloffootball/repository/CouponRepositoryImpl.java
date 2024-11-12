package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.jparepository.JpaCouponRepository;
import com.flutter.alloffootball.common.jparepository.JpaUserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaUserCouponRepository jpaUserCouponRepository;
    private final JpaCouponRepository jpaCouponRepository;

    @Override
    public List<UserCoupon> findAllByNotUseCoupon(Long userId) {
        return jpaUserCouponRepository.findAllByUser_IdAndCouponUseAndExpireDateAfter(userId, 'N', LocalDateTime.now());
    }

    @Override
    public void refundCoupon(UserCoupon userCoupon, LocalDateTime now) {
        if (userCoupon == null) return;
        // 만료된 쿠폰은 삭제
        if (now.isAfter(userCoupon.getExpireDate())) {
            jpaUserCouponRepository.delete(userCoupon);
        } else {
            userCoupon.notUse();
        }
    }
}
