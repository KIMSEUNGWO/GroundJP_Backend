package com.flutter.alloffootball.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum RefundPolicy {

    P100("무료취소", i -> i),
    P80("80% 환불", i -> (int) Math.round(0.8 * i)),
    P20("20% 환불", i -> (int) Math.round(0.2 * i)),
    P0("환불 불가", i -> 0);

    private final String message;
    private final Function<Integer, Integer> calculate;

    public static RefundPolicy getPolicy(LocalDateTime now, LocalDateTime matchDate) {
        Duration duration = Duration.between(now, matchDate);
        long minutes = duration.toMinutes();

        if (duration.toDays() >= 2) return P100;  // 2일 전이면 P100
        if (duration.toDays() >= 1) return P80;   // 1일 전이면 P80
        if (minutes >= 90) return P20;   // 당일 ~ 90분 전이면 P20
        return P0;    // 90분 이내면 P0
    }

    public int calculate(int price) {
        return calculate.apply(price);
    }
}
