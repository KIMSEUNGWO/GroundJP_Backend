package com.flutter.alloffootball.querydsl;

import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.enums.SexType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface QueryDslOrderRepository {

    List<Order> findDistinctDaysWithDataInMonth(long userId, LocalDateTime startDate, LocalDateTime endDate);

}
