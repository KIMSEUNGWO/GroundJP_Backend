package com.flutter.alloffootball.common.component;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Component
public class DateRangeUtil {

    public static LocalDateTime getStartOfMonth(LocalDateTime dateTime) {
        // 해당 월의 첫 번째 날의 자정을 반환
        return dateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    public static LocalDateTime getEndOfMonth(LocalDateTime dateTime) {
        // 해당 월의 마지막 날의 마지막 시간을 반환
        return dateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }
}
