package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.statistics.UserStatisticsDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserStatisticsDetailRepository extends JpaRepository<UserStatisticsDetail, Long> {
}
