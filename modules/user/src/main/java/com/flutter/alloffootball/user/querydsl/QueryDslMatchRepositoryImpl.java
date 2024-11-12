package com.flutter.alloffootball.user.querydsl;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.user.dto.match.RequestSearchMatch;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.region.Region;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.flutter.alloffootball.common.domain.match.QMatch.*;

@Repository
@RequiredArgsConstructor
public class QueryDslMatchRepositoryImpl implements QueryDslMatchRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Match> search(RequestSearchMatch searchMatch, Pageable pageable) {

        LocalDateTime searchDate = searchMatch.getDate();
        LocalDateTime endDate = LocalDateTime.of(searchDate.toLocalDate(), LocalTime.MAX.minusSeconds(1));
        return query.select(match)
            .from(match)
            .where(
                match.matchDate.between(searchDate, endDate),
                conditionSex(searchMatch.getSex()),
                conditionRegion(searchMatch.getRegion()))
            .orderBy(match.matchDate.asc())
            .fetch();
    }

    private BooleanExpression conditionSex(SexType sex) {
        // 남자 또는 여자를 선택하지 않았을 경우 모두 조회
        if (sex == null) return null;
        return match.matchSex.eq(sex);
    }
    private BooleanExpression conditionRegion(Region region) {
        // 지역을 선택하지 않았을 경우 모두 조회
        if (region == null) return null;
        return match.field.address.region.eq(region);
    }

}
