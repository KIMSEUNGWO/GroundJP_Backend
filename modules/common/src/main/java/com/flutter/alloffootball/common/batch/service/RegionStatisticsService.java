package com.flutter.alloffootball.common.batch.service;

import com.flutter.alloffootball.common.batch.dto.ResponseRegionStatistics;
import com.flutter.alloffootball.common.batch.repository.RegionStatisticsRepository;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.domain.statistics.RegionStatistics;
import com.flutter.alloffootball.common.enums.OrderStatus;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.common.jparepository.JpaOrderRepository;
import com.flutter.alloffootball.common.jparepository.JpaRegionStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RegionStatisticsService {

    private final RegionStatisticsRepository regionStatisticsRepository;
    private final JpaRegionStatisticsRepository jpaRegionStatisticsRepository;


    public void refresh() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 이미 해당 날짜의 통계가 있는지 확인
        if (jpaRegionStatisticsRepository.existsByStatsDate(yesterday)) {
            log.info("Statistics for {} already exists", yesterday);
            return;
        }

        // DB에서 직접 통계 집계
        List<RegionStatistics> result = regionStatisticsRepository.aggregateRegionStatistics(
            LocalDateTime.of(yesterday, LocalTime.MIN),
            LocalDateTime.of(yesterday, LocalTime.MAX),
            yesterday
        );

        jpaRegionStatisticsRepository.saveAll(result);

    }

    public List<ResponseRegionStatistics> getRegionStatisticsRangeClosed(LocalDate startDate, LocalDate endDate) {
        return jpaRegionStatisticsRepository.findByStatsDateBetween(startDate, endDate)
            .stream()
            .map(ResponseRegionStatistics::new)
            .toList();

    }
}
