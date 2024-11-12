package com.flutter.alloffootball.common.batch.dto;

import com.flutter.alloffootball.common.domain.user.User;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

@AllArgsConstructor
public enum UserStatisticsCategory {

    GENDER("성별", user -> switch (user.getUserInfo().getSex()) {
        case MALE -> "남자";
        case FEMALE -> "여자";
    }),
    SOCIAL("소셜", user -> switch (user.getSocial().getProvider()) {
        case LINE -> "라인";
        case KAKAO -> "카카오";
        case APPLE -> "애플";
    }),
    AGE("연령", user -> switch (Period.between(user.getUserInfo().getBirth(), LocalDate.now()).getYears() / 10) {
        case 0, 1 -> "10대";
        case 2 -> "20대";
        case 3 -> "30대";
        case 4 -> "40대";
        case 5 -> "50대";
        default -> "60대 이상";
    });

    private final String title;
    private final Function<User, String> function;

    public String apply(User user) {
        return function.apply(user);
    }
}
