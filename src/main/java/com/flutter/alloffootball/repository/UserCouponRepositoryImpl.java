package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.exception.*;
import com.flutter.alloffootball.component.CouponCalculator;
import com.flutter.alloffootball.dto.coupon.ResponseCouponUse;
import com.flutter.alloffootball.common.jparepository.JpaUserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponRepository {

    private final JpaUserCouponRepository jpaUserCouponRepository;
    private final CouponCalculator couponCalculator;

    @Override
    public UserCoupon findById(Long couponId) {
        if (couponId == null) return null;
        return jpaUserCouponRepository.findById(couponId)
            .orElseThrow(() -> new CouponException(CouponError.COUPON_NOT_EXISTS));
    }

    @Transactional(noRollbackFor = CouponExpireException.class)
    @Override
    public ResponseCouponUse useCoupon(UserCoupon userCoupon, long userId, LocalDateTime now, int totalPrice) {
        // 쿠폰을 선택하지 않았다면 경기 최종가격으로 반환
        if (userCoupon == null) return null;

        userCouponValid(userCoupon, userId, now);

        userCoupon.use();

        return ResponseCouponUse.builder()
            .title(userCoupon.getCoupon().getTitle())
            .discount(-1 * couponCalculator.discount(totalPrice, userCoupon.getCoupon().getDiscountPer()))
            .totalPrice(couponCalculator.total(totalPrice, userCoupon.getCoupon().getDiscountPer()))
            .build();
    }

    private void userCouponValid(UserCoupon userCoupon, long userId, LocalDateTime now) {
        // 자신의 쿠폰이 아닌 경우 ( 이 예외는 데이터 조작일 경우만 해당 됨 )
        if (userCoupon.getUser().getId() != userId) throw new InvalidException(DefaultError.INVALID_REQUEST);
        // 이미 사용한 경우
        if (userCoupon.isUse()) throw new CouponException(CouponError.COUPON_ALREADY_USE);
        // 이미 만료된 경우
        if (userCoupon.getExpireDate().isBefore(now)){
            jpaUserCouponRepository.delete(userCoupon);
            throw new CouponExpireException();
        }
    }

}
