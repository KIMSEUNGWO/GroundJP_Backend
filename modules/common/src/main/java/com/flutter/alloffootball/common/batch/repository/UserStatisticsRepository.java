package com.flutter.alloffootball.common.batch.repository;

import com.flutter.alloffootball.common.domain.statistics.UserStatistics;
import com.flutter.alloffootball.common.batch.dto.UserStatisticsCategory;
import com.flutter.alloffootball.common.batch.dto.UserStatisticsData;
import com.flutter.alloffootball.common.domain.statistics.UserStatisticsDetail;
import com.flutter.alloffootball.common.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class UserStatisticsRepository {

    public List<UserStatisticsData> getStatistics(UserStatistics userStatistics, UserStatisticsCategory category) {
       return userStatistics.getUserStatisticsDetails().stream()
           .filter(detail -> detail.getCategory().equals(category))
           .map(x -> new UserStatisticsData(x.getKey(), x.getValue()))
           .toList();
    }

    public void createStatistics(UserStatistics userStatistics, List<User> userAll) {
        List<UserStatisticsDetail> result = new ArrayList<>();

        Map<String, Long> gender = new HashMap<>();
        Map<String, Long> social = new HashMap<>();
        Map<String, Long> age = new HashMap<>();


        for (User user : userAll) {
            String genderKey = UserStatisticsCategory.GENDER.apply(user);
            gender.put(genderKey, gender.getOrDefault(genderKey, 0L) + 1);

            String socialKey = UserStatisticsCategory.SOCIAL.apply(user);
            social.put(socialKey, social.getOrDefault(socialKey, 0L) + 1);

            String ageKey = UserStatisticsCategory.AGE.apply(user);
            age.put(ageKey, age.getOrDefault(ageKey, 0L) + 1);
        }


        getUserStatisticsDetailStream(userStatistics, UserStatisticsCategory.GENDER, gender).forEach(result::add);
        getUserStatisticsDetailStream(userStatistics, UserStatisticsCategory.SOCIAL, social).forEach(result::add);
        getUserStatisticsDetailStream(userStatistics, UserStatisticsCategory.AGE, age).forEach(result::add);

        userStatistics.setUserStatisticsDetails(result);
    }

    private Stream<UserStatisticsDetail> getUserStatisticsDetailStream(UserStatistics userStatistics, UserStatisticsCategory category, Map<String, Long> map) {
        return map.entrySet().stream().map(x -> UserStatisticsDetail.builder()
            .userStatistics(userStatistics)
            .category(category)
            .key(x.getKey())
            .value(x.getValue())
            .build());
    }


}
