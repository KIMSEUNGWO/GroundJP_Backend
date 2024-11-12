package com.flutter.alloffootball.common.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ResponseUserStatistics {

    private LocalDate statsDate;
    private long userCnt;
    private long delta;

    private List<UserStatisticsData> genderStats;
    private List<UserStatisticsData> socialStats;
    private List<UserStatisticsData> ageStats;
}
