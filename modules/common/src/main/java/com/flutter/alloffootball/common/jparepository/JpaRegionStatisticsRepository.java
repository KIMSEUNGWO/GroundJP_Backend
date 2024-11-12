package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.statistics.RegionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaRegionStatisticsRepository extends JpaRepository<RegionStatistics, Long> {

    boolean existsByStatsDate(LocalDate yesterday);
    List<RegionStatistics> findByStatsDateBetween(LocalDate startDate, LocalDate endDate);
}
