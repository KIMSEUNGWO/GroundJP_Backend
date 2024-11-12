package com.flutter.alloffootball.dto.order;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.dto.coupon.ResponseCouponUse;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@ToString
public class ResponseOrderResult {

    // 이용 금액
    private final int totalPrice;

    @Nullable
    private ResponseCouponUse coupon;

    // 최종 결재 금맥
    private final int finalPrice;

    // 남은 잔액
    private final int remainCash;

    public ResponseOrderResult(Match match, Order order, User user, ResponseCouponUse couponUse) {
        this.totalPrice = match.getPrice();
        this.coupon = couponUse;
        this.finalPrice = order.getPrice();
        this.remainCash = user.getCash();
    }
}
