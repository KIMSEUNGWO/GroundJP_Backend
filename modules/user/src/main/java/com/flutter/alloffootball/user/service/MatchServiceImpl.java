package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.user.component.MatchStatisticsBuilder;
import com.flutter.alloffootball.user.dto.match.*;
import com.flutter.alloffootball.user.dto.order.ResponseOrderSimp;
import com.flutter.alloffootball.user.repository.MatchRepository;
import com.flutter.alloffootball.user.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<ResponseMatchView> search(RequestSearchMatch searchMatch, Pageable pageable) {
        return matchRepository.findAllByMatchData(searchMatch, pageable).stream()
            .map(ResponseMatchView::new)
            .toList();

    }

    // TODO MatchData 확인해야되는 Service
    @Override
    public List<ResponseMatchSimp> findAllByFieldIdToMatchData(long fieldId, Pageable pageable) {
        return matchRepository.findAllByFieldIdToMatchData(fieldId, pageable).stream()
            .map(ResponseMatchSimp::new)
            .toList();
    }

    @Override
    public ResponseMatchDetails getMatchDetails(long matchId, Long userId) {
        Match match = findByMatchId(matchId);
        boolean isParticipation = orderRepository.isAlreadyJoin(matchId, userId);
        RequestMatchStatistics statistics = getStatisticsIfParticipating(match, isParticipation);
        return new ResponseMatchDetails(match, isParticipation, statistics);
    }

    @Override
    public ResponseOrderSimp getOrderSimp(long matchId, Long userId) {
        Match match = findByMatchId(matchId);
        return new ResponseOrderSimp(match);
    }

    private RequestMatchStatistics getStatisticsIfParticipating(Match match, boolean isParticipating) {
        if (!isParticipating) return null;
        Stream<User> participants = orderRepository.getParticipants(match);
        return MatchStatisticsBuilder.build(participants);
    }

    private Match findByMatchId(long matchId) {
        return matchRepository.findById(matchId);
    }

}
