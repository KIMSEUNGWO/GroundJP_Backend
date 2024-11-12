package com.flutter.alloffootball.service;

import com.flutter.alloffootball.dto.coupon.ResponseCoupon;
import com.flutter.alloffootball.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public List<ResponseCoupon> findAllByCouponsOnlyNotUse(Long userId) {
        return couponRepository.findAllByNotUseCoupon(userId)
            .stream()
            .map(ResponseCoupon::new)
            .toList();
    }
}
