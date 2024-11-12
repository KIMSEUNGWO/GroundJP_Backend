package com.flutter.alloffootball.common.querydsl;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.querydsl.suport.QueryDSLRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class QueryMatchSupport extends QueryDSLRepositorySupport {

    public QueryMatchSupport() {
        super(Match.class);
    }
}
