package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.exception.MatchError;
import com.flutter.alloffootball.common.exception.MatchException;
import com.flutter.alloffootball.common.jparepository.JpaMatchRepository;
import com.flutter.alloffootball.user.querydsl.QueryDslMatchRepository;
import com.flutter.alloffootball.user.dto.match.RequestSearchMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchRepositoryImpl implements MatchRepository {

    private final JpaMatchRepository jpaMatchRepository;
    private final QueryDslMatchRepository queryDslMatchRepository;

    @Override
    public List<Match> findAllByMatchData(RequestSearchMatch searchMatch, Pageable pageable) {
        return queryDslMatchRepository.search(searchMatch, pageable);
    }

    @Override
    public Page<Match> findAllByFieldIdToMatchData(long fieldId, Pageable pageable) {
        return jpaMatchRepository.findAllByField_IdAndMatchDateAfter(fieldId, pageable, LocalDateTime.now());
    }

    @Override
    public Match findById(Long matchId) {
        if (matchId == null) throw new MatchException(MatchError.MATCH_NOT_EXISTS);

        return jpaMatchRepository.findById(matchId)
            .orElseThrow(() -> new MatchException(MatchError.MATCH_NOT_EXISTS));
    }

    @Override
    public void save(Match saveMatch) {
        jpaMatchRepository.save(saveMatch);
    }
}
