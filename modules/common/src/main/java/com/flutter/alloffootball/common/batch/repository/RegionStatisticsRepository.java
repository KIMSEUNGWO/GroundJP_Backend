package com.flutter.alloffootball.common.batch.repository;

import com.flutter.alloffootball.common.domain.field.QField;
import com.flutter.alloffootball.common.domain.match.QMatch;
import com.flutter.alloffootball.common.domain.orders.QOrder;
import com.flutter.alloffootball.common.domain.statistics.RegionStatistics;
import com.flutter.alloffootball.common.enums.OrderStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.flutter.alloffootball.common.domain.field.QField.*;
import static com.flutter.alloffootball.common.domain.match.QMatch.*;
import static com.flutter.alloffootball.common.domain.orders.QOrder.*;
import static com.flutter.alloffootball.common.enums.OrderStatus.*;

@Repository
@RequiredArgsConstructor
public class RegionStatisticsRepository {

    private final JPAQueryFactory query;


    public List<RegionStatistics> aggregateRegionStatistics(LocalDateTime from, LocalDateTime to, LocalDate yesterday) {
        return query.select(Projections.fields(RegionStatistics.class,
                field.address.region,
                new CaseBuilder().when(order.orderStatus.eq(COMPLETE)).then(1L).otherwise(0L).sum().as("completeCnt"),
                new CaseBuilder().when(order.orderStatus.eq(CANCEL)).then(1L).otherwise(0L).sum().as("cancelCnt"),
                Expressions.asDate(yesterday).as("statsDate")
                ))
            .from(order)
            .join(order.match, match)
            .join(match.field, field)
            .where(order.createDate.between(from, to), order.orderStatus.in(COMPLETE, CANCEL))
            .groupBy(field.address.region)
            .fetch();
    }
}
