package com.flutter.alloffootball.user.querydsl;

import com.flutter.alloffootball.common.domain.field.Field;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.flutter.alloffootball.common.domain.field.QField.*;

@Repository
@RequiredArgsConstructor
public class QueryDslFieldRepositoryImpl implements QueryDslFieldRepository {

    private final JPAQueryFactory query;


    @Override
    public List<Field> search(String word) {
        StringExpression keywordExpression = getKeywordExpression(word);

        return query.select(field)
            .from(field)
            .where(getLowerAndReplace(field.title).like(keywordExpression))
            .fetch();
    }

    private StringExpression getKeywordExpression(String word) {
        return Expressions.asString("%" + word.toLowerCase().replace(" ", "") + "%");
    }

    private StringExpression getLowerAndReplace(StringExpression tuple) {
        return Expressions.stringTemplate("replace(lower({0}), ' ', '')", tuple);
    }
}
