package com.flutter.alloffootball.service;

import com.flutter.alloffootball.common.domain.orders.CancelOrder;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.enums.CashType;
import com.flutter.alloffootball.common.enums.RefundPolicy;
import com.flutter.alloffootball.repository.CouponRepository;
import com.flutter.alloffootball.repository.OrderRepository;
import com.flutter.alloffootball.repository.PaymentRepository;
import com.flutter.alloffootball.repository.RefundRepository;
import com.flutter.alloffootball.dto.order.RequestCancelOrder;
import com.flutter.alloffootball.dto.refund.ResponseRefundResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RefundServiceImpl implements RefundService {

    private final OrderRepository orderRepository;

    private final RefundRepository refundRepository;
    private final CouponRepository couponRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public synchronized ResponseRefundResult cancelOrder(RequestCancelOrder cancelOrder, Long userId) {
        Order order = orderRepository.findByUserIdAndMatchIdAndOrderStatusIsUSE(userId, cancelOrder.getMatchId());
        LocalDateTime now = LocalDateTime.now();
        int payAmount = order.getPrice();

        // 쿠폰 적용 여부 확인 및 현재시간 기준 만료된 쿠폰은 삭제처리
        couponRepository.refundCoupon(order.getUserCoupon(), now);

        // 현재시간 기준으로 환불정책 분류
        RefundPolicy refundPolicy = RefundPolicy.getPolicy(now, order.getMatch().getMatchDate());

        // 실제 결제금액에 환불정책이 적용된 금액 계산
        int refundAmount = refundPolicy.calculate(payAmount);

        // CancelOrder DB 생성 및 저장
        CancelOrder saveCancelOrder = CancelOrder.builder()
            .user(order.getUser())
            .match(order.getMatch())
            .refundAmount(refundAmount)
            .build();
        refundRepository.saveCancelOrder(saveCancelOrder);

        // Receipt 생성
        paymentRepository.receipt(order.getUser(), refundPolicy.getMessage(), CashType.REFUND, refundAmount);

        // Order 삭제처리
        order.setCancel();
        orderRepository.refreshMatchStatus(order.getMatch());

        return ResponseRefundResult
            .builder()
            .payAmount(payAmount)
            .refundAmount(refundAmount)
            .build();
    }
}
