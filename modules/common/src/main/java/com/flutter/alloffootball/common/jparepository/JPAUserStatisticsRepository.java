package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.statistics.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface JPAUserStatisticsRepository extends JpaRepository<UserStatistics, Long> {

    Optional<UserStatistics> findFirstByOrderByIdDesc();
    Optional<UserStatistics> findFirstByStatsDateOrderByIdDesc(LocalDate localDate);
}
