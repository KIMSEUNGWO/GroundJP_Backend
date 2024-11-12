package com.flutter.alloffootball.common.batch.service;

import com.flutter.alloffootball.common.batch.dto.ResponseUserStatistics;
import com.flutter.alloffootball.common.batch.repository.UserStatisticsRepository;
import com.flutter.alloffootball.common.domain.statistics.UserStatistics;
import com.flutter.alloffootball.common.batch.dto.UserStatisticsCategory;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.jparepository.JPAUserStatisticsRepository;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserStatisticsService {

    private final JPAUserStatisticsRepository jpaUserStatisticsRepository;
    private final JpaUserRepository jpaUserRepository;

    private final UserStatisticsRepository userStatisticsRepository;

    public void refresh() {
        List<User> userAll = jpaUserRepository.findAll();

        Long beforeDelta = jpaUserStatisticsRepository.findFirstByOrderByIdDesc()
            .map(UserStatistics::getUserCnt)
            .orElse(0L);


        UserStatistics saveUserStatistics = UserStatistics.builder()
            .statsDate(LocalDate.now())
            .userCnt(userAll.size())
            .delta(userAll.size() - beforeDelta)
            .build();

        userStatisticsRepository.createStatistics(saveUserStatistics, userAll);

        jpaUserStatisticsRepository.save(saveUserStatistics);
    }

    public ResponseUserStatistics getUserStatistics(LocalDate localDate) {
        return jpaUserStatisticsRepository.findFirstByStatsDateOrderByIdDesc(localDate)
            .map(x -> ResponseUserStatistics.builder()
                .userCnt(x.getUserCnt())
                .delta(x.getDelta())
                .statsDate(x.getStatsDate())
                .genderStats(userStatisticsRepository.getStatistics(x, UserStatisticsCategory.GENDER))
                .socialStats(userStatisticsRepository.getStatistics(x, UserStatisticsCategory.SOCIAL))
                .ageStats(userStatisticsRepository.getStatistics(x, UserStatisticsCategory.AGE))
                .build())
            .orElse(ResponseUserStatistics.builder().build());
    }
}
