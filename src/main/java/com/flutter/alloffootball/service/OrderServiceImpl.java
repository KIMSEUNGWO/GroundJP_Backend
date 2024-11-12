package com.flutter.alloffootball.service;

import com.flutter.alloffootball.common.component.DateRangeUtil;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.CashType;
import com.flutter.alloffootball.component.OrderCalculator;
import com.flutter.alloffootball.dto.coupon.ResponseCouponUse;
import com.flutter.alloffootball.dto.match.ResponseMatchView;
import com.flutter.alloffootball.dto.order.RequestOrder;
import com.flutter.alloffootball.dto.order.ResponseOrderResult;
import com.flutter.alloffootball.common.enums.OrderStatus;
import com.flutter.alloffootball.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final OrderRepository orderRepository;
    private final UserCouponRepository userCouponRepository;

    @Override
    public synchronized ResponseOrderResult order(RequestOrder requestOrder, long userId, LocalDateTime now) {
        Match match = matchRepository.findById(requestOrder.getMatchId());
        User user = userRepository.findById(userId);

        // 1. 가격 계산
        OrderCalculator calculator = calculateFinalPrice(match, requestOrder.getCouponId(), userId, now);

        // 2. 주문 생성 및 검증
        Order saveOrder = createOrder(match, user, calculator);

        // 3. 주문 후처리
        processOrderComplete(match, user, calculator.getFinalPrice());

        return new ResponseOrderResult(match, saveOrder, user, calculator.getResponseCouponUse());
    }

    private OrderCalculator calculateFinalPrice(Match match, Long couponId, Long userId, LocalDateTime now) {
        int originalPrice = match.getPrice();

        if (couponId == null) {
            return new OrderCalculator(originalPrice, null, null);
        }

        UserCoupon userCoupon = userCouponRepository.findById(couponId);

        ResponseCouponUse couponUse = userCouponRepository.useCoupon(userCoupon, userId, now, originalPrice);

        return new OrderCalculator(
            couponUse != null ? couponUse.getTotalPrice() : originalPrice,
            couponUse,
            userCoupon
        );
    }

    private Order createOrder(Match match, User user, OrderCalculator priceResult) {
        orderRepository.valid(match, user, priceResult.getFinalPrice());

        Order order = Order.builder()
            .match(match)
            .user(user)
            .price(priceResult.getFinalPrice())
            .userCoupon(priceResult.getUserCoupon())
            .orderStatus(OrderStatus.USE)
            .build();

        return orderRepository.save(order);
    }

    private void processOrderComplete(Match match, User user, int finalPrice) {
        orderRepository.refreshMatchStatus(match);
        paymentRepository.receipt(user, "경기 참여", CashType.USE, finalPrice);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Integer, List<ResponseMatchView>> getHistory(LocalDateTime date, User user) {
        LocalDateTime startDate = DateRangeUtil.getStartOfMonth(date);
        LocalDateTime endDate = DateRangeUtil.getEndOfMonth(date);

        return orderRepository.getHistory(user.getId(), startDate, endDate)
            .stream()
            .map(order -> new ResponseMatchView(order.getMatch()))
            .collect(Collectors.groupingBy(matchView -> matchView.getMatchDate().getDayOfMonth()));
    }

    @Override
    public List<ResponseMatchView> findAllByUserIdAndMatchDateAfter(Long userId, LocalDateTime now) {
        return orderRepository.findAllByMatchSoon(userId, now)
            .stream()
            .map(order -> new ResponseMatchView(order.getMatch()))
            .toList();
    }

}
