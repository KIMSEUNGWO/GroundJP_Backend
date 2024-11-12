package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.OrderStatus;
import com.flutter.alloffootball.common.exception.OrderError;
import com.flutter.alloffootball.common.exception.OrderException;
import com.flutter.alloffootball.common.jparepository.JpaOrderRepository;
import com.flutter.alloffootball.user.querydsl.QueryDslOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final QueryDslOrderRepository queryDslOrderRepository;

    @Override
    public boolean isAlreadyJoin(long matchId, CustomUserDetails userDetails) {
        if (userDetails == null) return false;
        return jpaOrderRepository.existsByMatch_IdAndUser_IdAndOrderStatus(matchId, userDetails.getUser().getId(), OrderStatus.USE);
    }

    @Override
    public void valid(Match match, User user, int price) {

        if (match.getMatchSex() != null && match.getMatchSex() != user.getUserInfo().getSex()) {
            throw new OrderException(OrderError.NOT_MATCHED_SEX);
        }

        // 이미 참가 중인 유저인 경우
        boolean alreadyJoin = jpaOrderRepository.existsByMatch_IdAndUser_IdAndOrderStatus(match.getId(), user.getId(), OrderStatus.USE);
        if (alreadyJoin) throw new OrderException(OrderError.ALREADY_JOIN);

        // 현재 보유한 캐시보다 적은 경우
        if (user.getCash() < price) throw new OrderException(OrderError.NOT_ENOUGH_CASH);

        // 이미 마감한 경우
        if (match.getMatchStatus() == MatchStatus.CLOSED) throw new OrderException(OrderError.ALREADY_CLOSED);

    }

    @Override
    public Order save(Order saveOrder) {
        return jpaOrderRepository.save(saveOrder);
    }


    @Override
    public List<Order> getHistory(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return queryDslOrderRepository.findDistinctDaysWithDataInMonth(userId, startDate, endDate);
    }

    @Override
    public List<Order> findAllByMatchSoon(Long userId, LocalDateTime now) {
        return jpaOrderRepository.findAllByUser_IdAndOrderStatusAndMatch_MatchDateAfterOrderByMatch_MatchDate(userId, OrderStatus.USE, now);
    }

    @Override
    public Order findByUserIdAndMatchIdAndOrderStatusIsUSE(Long userId, Long matchId) {
        if (userId == null || matchId == null) throw new OrderException(OrderError.ORDER_NOT_EXISTS);
        return jpaOrderRepository.findByUser_IdAndMatch_IdAndOrderStatus(userId, matchId, OrderStatus.USE)
            .orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_EXISTS));
    }

    @Override
    public void delete(Order order) {
        jpaOrderRepository.delete(order);
    }

    @Override
    public void refreshMatchStatus(Match match) {
        long orderUseCount = jpaOrderRepository.countByMatchAndOrderStatus(match, OrderStatus.USE);
        match.refreshEnabledOrder(orderUseCount);
    }

    @Override
    public Stream<User> getParticipants(Match match) {
        return jpaOrderRepository.findAllByMatchAndCancelDateIsNull(match).stream().map(Order::getUser);
    }
}
