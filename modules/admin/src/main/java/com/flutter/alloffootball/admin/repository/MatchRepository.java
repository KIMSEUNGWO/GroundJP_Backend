package com.flutter.alloffootball.admin.repository;

import com.flutter.alloffootball.admin.dto.match.ResponseViewMatchUser;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.exception.MatchError;
import com.flutter.alloffootball.common.exception.MatchException;
import com.flutter.alloffootball.common.jparepository.JpaMatchRepository;
import com.flutter.alloffootball.common.jparepository.JpaOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchRepository {

    private final JpaMatchRepository jpaMatchRepository;
    private final JpaOrderRepository jpaOrderRepository;

    public long save(Match saveMatch) {
        return jpaMatchRepository.save(saveMatch).getId();
    }

    public Match matchFindById(long matchId) {
        return jpaMatchRepository.findById(matchId)
            .orElseThrow(() -> new MatchException(MatchError.MATCH_NOT_EXISTS));
    }

    public List<ResponseViewMatchUser> getViewMatchUser(Match match) {
        return jpaOrderRepository.findAllByMatchAndCancelDateIsNull(match)
            .stream().map(ResponseViewMatchUser::new)
            .sorted(Comparator.comparingLong(ResponseViewMatchUser::getUserId))
            .toList();
    }
}
