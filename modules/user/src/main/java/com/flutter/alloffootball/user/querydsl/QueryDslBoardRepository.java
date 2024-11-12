package com.flutter.alloffootball.user.querydsl;

import com.flutter.alloffootball.common.domain.board.Board;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.user.dto.board.RequestSearchBoard;
import com.flutter.alloffootball.common.querydsl.suport.QueryDSLRepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.flutter.alloffootball.common.domain.board.QBoard.*;
import static com.flutter.alloffootball.common.domain.user.QProfile.*;
import static com.flutter.alloffootball.common.domain.user.QUser.*;

@Repository
public class QueryDslBoardRepository extends QueryDSLRepositorySupport {

    private final JPAQueryFactory query;

    public QueryDslBoardRepository(JPAQueryFactory query) {
        super(Board.class);
        this.query = query;
    }

    public List<Board> search(RequestSearchBoard searchBoard, Pageable pageable) {
        return selectFrom(board)
            .join(board.user, user).fetchJoin()
            .join(user.profile, profile).fetchJoin()
            .where(conditionRegion(searchBoard.getRegion()))
            .orderBy(board.createDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression conditionRegion(Region region) {
        if (region == null) return null;
        return board.region.eq(region);
    }
}
