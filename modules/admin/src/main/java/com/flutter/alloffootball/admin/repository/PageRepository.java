package com.flutter.alloffootball.admin.repository;

import com.flutter.alloffootball.admin.dto.field.RequestSearchField;
import com.flutter.alloffootball.admin.dto.field.ResponseSearchField;
import com.flutter.alloffootball.admin.dto.match.RequestSearchMatch;
import com.flutter.alloffootball.admin.dto.match.ResponseSearchMatch;
import com.flutter.alloffootball.admin.dto.notice.ResponseNoticeListView;
import com.flutter.alloffootball.admin.dto.user.RequestSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseUserOrder;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.OrderStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.common.jparepository.JpaOrderRepository;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import com.flutter.alloffootball.common.querydsl.QueryFieldSupport;
import com.flutter.alloffootball.common.querydsl.QueryMatchSupport;
import com.flutter.alloffootball.common.querydsl.QueryNoticeSupport;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.flutter.alloffootball.common.domain.field.QField.field;
import static com.flutter.alloffootball.common.domain.match.QMatch.match;

@Repository
@RequiredArgsConstructor
public class PageRepository {

    private final JPAQueryFactory query;
    private final JpaOrderRepository jpaOrderRepository;
    private final JpaUserRepository jpaUserRepository;

    private final QueryFieldSupport fieldSupport;
    private final QueryMatchSupport matchSupport;
    private final QueryNoticeSupport noticeSupport;


    public Page<ResponseSearchField> findAllBySearchField(RequestSearchField data, Pageable pageable) {
        BooleanExpression compareRegion = conditionRegion(data.getRegion());
        BooleanExpression keywordExpression = getKeywordExpression(data.getWord(), field.title);

        return fieldSupport.applyPagination(field, pageable,
            query -> query.selectFrom(field)
                .where(keywordExpression, compareRegion)
                .orderBy(field.id.desc())
        ).map(ResponseSearchField::new);

    }

    public Page<ResponseSearchMatch> findAllBySearchMatch(RequestSearchMatch data, Pageable pageable) {
        BooleanExpression compareRegion = conditionRegion(data.getRegion());
        BooleanExpression compareSex = conditionSex(data.getSex());
        BooleanExpression compareStatus = conditionMatchStatus(data.getState());

        BooleanExpression keywordExpression = getKeywordExpression(data.getWord(), field.title);

        Page<Match> matches = matchSupport.applyPagination(match, pageable,
            query -> query.selectFrom(match)
                .where(
                    keywordExpression,
                    compareRegion,
                    compareSex,
                    compareStatus,
                    match.matchDate.between(
                        LocalDateTime.of(data.getStartDate(), LocalTime.MIN),
                        LocalDateTime.of(data.getEndDate(), LocalTime.MAX)
                    )
                )
                .orderBy(match.matchDate.asc())
        );

        Page<ResponseSearchMatch> result = matches.map(match -> {
            long countPerson = jpaOrderRepository.countByMatchAndOrderStatus(match, OrderStatus.USE);
            return new ResponseSearchMatch(match, countPerson);
        });

        System.out.println("result = " + result);
        return result;
    }

    public Page<ResponseSearchUser> findAllBySearchUser(RequestSearchUser data, Pageable pageable) {
        return jpaUserRepository.findAllByNicknameContainingIgnoreCaseOrderByIdDesc(data.getWord(), pageable)
            .map(ResponseSearchUser::new);
    }

    public Page<ResponseUserOrder> findAllByUserOrder(Long userId, Pageable pageable) {
        return jpaOrderRepository.findAllByUser_idOrderByCreateDateDesc(userId, pageable)
            .map(ResponseUserOrder::new);
    }

    private BooleanExpression getKeywordExpression(String word, StringExpression compareWord) {
        if (word == null || word.isEmpty()) return null;
        return getReplaceExpression(compareWord).containsIgnoreCase(word.replace(" ", ""));
    }

    private StringExpression getReplaceExpression(StringExpression tuple) {
        return Expressions.stringTemplate("replace({0}, ' ', '')", tuple);
    }

    private BooleanExpression conditionRegion(Region region) {
        // 지역을 선택하지 않았을 경우 모두 조회
        if (region == null) return null;
        return field.address.region.eq(region);
    }
    private BooleanExpression conditionSex(SexType sexType) {
        // 성별을 선택하지 않았을 경우 모두 조회
        if (sexType == null) return null;
        return match.matchSex.eq(sexType);
    }
    private BooleanExpression conditionMatchStatus(MatchStatus matchStatus) {
        // 상태를 선택하지 않았을 경우 모두 조회
        if (matchStatus == null) return null;
        return match.matchStatus.eq(matchStatus);
    }

    public Page<ResponseNoticeListView> findAllBySearchNotice(Pageable pageable) {
        return noticeSupport.findAllBySearchNotice(pageable).map(ResponseNoticeListView::new);
    }
}
