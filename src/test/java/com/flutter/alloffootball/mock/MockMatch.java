package com.flutter.alloffootball.mock;

import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.jparepository.JpaMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MockMatch {

    private final JpaMatchRepository jpaMatchRepository;

    @Autowired
    public MockMatch(JpaMatchRepository jpaMatchRepository) {
        this.jpaMatchRepository = jpaMatchRepository;
    }

    public Match mockMatch(Field field, LocalDateTime matchDate, MatchStatus matchStatus, SexType matchSex, int matchTime, int matchCount, int personCount, int price) {
        Match saveMatch = Match.builder()
            .field(field)
            .matchDate(matchDate)
            .matchStatus(matchStatus)
            .matchTime(matchTime)
            .matchSex(matchSex)
            .matchCount(matchCount)
            .personCount(personCount)
            .price(price)
            .build();
        return jpaMatchRepository.save(saveMatch);
    }
}