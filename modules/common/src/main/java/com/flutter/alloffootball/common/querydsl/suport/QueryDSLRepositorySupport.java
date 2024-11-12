package com.flutter.alloffootball.common.querydsl.suport;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

@Repository
public abstract class QueryDSLRepositorySupport {

    private final Class<?> domainClass;
    @Getter
    private Querydsl querydsl;
    @Getter
    private JPAQueryFactory query;

    public QueryDSLRepositorySupport(Class<?> domainClass) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        this.domainClass = domainClass;
    }

    @Autowired
    public void setEm(EntityManager em) {
        Assert.notNull(em, "EntityManager must not be null!");
        JpaEntityInformation<?, ?> entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, em);
        SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        EntityPath<?> path = resolver.createPath(entityInformation.getJavaType());
        this.querydsl = new Querydsl(em, new PathBuilder<>(path.getType(), path.getMetadata()));
        this.query = new JPAQueryFactory(em);
    }
    @PostConstruct
    public void validate() {
        Assert.notNull(querydsl, "Querydsl must not be null!");
        Assert.notNull(query, "QueryFactory must not be null!");
    }

    public <T> JPAQuery<T> select(Expression<T> expr) {
        return getQuery().select(expr);
    }
    public <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return getQuery().selectFrom(from);
    }

    public <T> Page<T> applyPagination(EntityPathBase<?> entity,
                                       Pageable pageable,
                                       Function<JPAQueryFactory, JPAQuery<T>> contentQuery) {
        JPAQuery<T> jpaContentQuery = contentQuery.apply(getQuery());

        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();

        JPAQuery<Long> countResult = getQuery().select(entity.count())
            .from(entity)
            .where(jpaContentQuery.getMetadata().getWhere());

        return PageableExecutionUtils.getPage(content, pageable,
            countResult::fetchOne);
    }

    public <T> Page<T> applyPagination(Pageable pageable,
                                       Function<JPAQueryFactory, JPAQuery<T>> contentQuery,
                                       Function<JPAQueryFactory, JPAQuery<Long>> countQuery) {
        JPAQuery<T> jpaContentQuery = contentQuery.apply(getQuery());

        List<T> content = getQuerydsl().applyPagination(pageable, jpaContentQuery).fetch();

        JPAQuery<Long> countResult = countQuery.apply(getQuery());

        return PageableExecutionUtils.getPage(content, pageable, countResult::fetchOne);
    }
}
