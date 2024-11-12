package com.flutter.alloffootball.querydsl;

import com.flutter.alloffootball.common.domain.match.QMatch;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.enums.OrderStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.flutter.alloffootball.common.domain.orders.QOrder.*;

@Repository
@RequiredArgsConstructor
public class QueryDslOrderRepositoryImpl implements QueryDslOrderRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Order> findDistinctDaysWithDataInMonth(long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return query.select(order)
            .from(order)
            .join(order.match, QMatch.match)
            .where(
                order.user.id.eq(userId)
                .and(order.match.matchDate.between(startDate, endDate))
                .and(order.orderStatus.eq(OrderStatus.USE))
            ).fetch();
    }
}
