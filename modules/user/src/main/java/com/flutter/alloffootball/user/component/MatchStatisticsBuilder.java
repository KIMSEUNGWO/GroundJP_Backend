package com.flutter.alloffootball.user.component;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.user.dto.match.RequestMatchStatistics;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 경기에 참가하는 참가자의 데이터 통계를 집계하는 class
 * static build 메소드에 통계데이터를 추가하면됨
 */
public class MatchStatisticsBuilder {

    private final Stream<User> userStream;
    private final RequestMatchStatistics statistics;

    public static RequestMatchStatistics build(Stream<User> userStream) {
        return new MatchStatisticsBuilder(userStream)
            .sexStatistics()
            .build();
    }
    private MatchStatisticsBuilder(Stream<User> userStream) {
        this.userStream = userStream;
        statistics = new RequestMatchStatistics();
    }

    private MatchStatisticsBuilder sexStatistics() {
        Map<SexType, Integer> result = userStream.collect(Collectors.groupingBy(
            user -> user.getUserInfo().getSex(),
            Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        statistics.setSexStatistics(result);
        return this;
    }

    private RequestMatchStatistics build() {
        return statistics;
    }
}
