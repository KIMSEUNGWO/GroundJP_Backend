package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.user.dto.match.RequestSearchMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchRepository {
    List<Match> findAllByMatchData(RequestSearchMatch searchMatch, Pageable pageable);
    Page<Match> findAllByFieldIdToMatchData(long fieldId, Pageable pageable);

    Match findById(Long matchId);

    void save(Match saveMatch);
}
