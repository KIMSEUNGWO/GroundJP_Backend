package com.flutter.alloffootball.querydsl;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.dto.match.RequestSearchMatch;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslMatchRepository {
    List<Match> search(RequestSearchMatch searchMatch, Pageable pageable);
}
