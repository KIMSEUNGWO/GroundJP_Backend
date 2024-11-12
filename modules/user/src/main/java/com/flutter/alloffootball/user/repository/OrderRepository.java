package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface OrderRepository {
    boolean isAlreadyJoin(long matchId, CustomUserDetails userDetails);

    void valid(Match match, User user, int price);

    Order save(Order saveOrder);

    List<Order> getHistory(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findAllByMatchSoon(Long userId, LocalDateTime now);

    Order findByUserIdAndMatchIdAndOrderStatusIsUSE(Long userId, Long matchId);

    void delete(Order order);

    void refreshMatchStatus(Match match);

    Stream<User> getParticipants(Match match);
}
