package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.match.RequestSaveMatchForm;
import com.flutter.alloffootball.admin.dto.match.ResponseViewMatch;
import com.flutter.alloffootball.admin.dto.match.ResponseViewMatchUser;
import com.flutter.alloffootball.admin.repository.FieldRepository;
import com.flutter.alloffootball.admin.repository.MatchRepository;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchService {

    private final FieldRepository fieldRepository;
    private final MatchRepository matchRepository;

    public long createMatch(long fieldId, RequestSaveMatchForm form) {
        Field field = fieldRepository.fieldFindById(fieldId);
        Match saveMatch = Match.builder()
            .field(field)
            .matchDate(LocalDateTime.of(form.getMatchDate(), form.getMatchHour()))
            .matchTime(form.getMatchTime())
            .matchSex(form.getSex())
            .matchCount(form.getMatchCount())
            .personCount(form.getMatchPerson())
            .price(form.getPrice())
            .matchStatus(MatchStatus.OPEN)
            .build();
        return matchRepository.save(saveMatch);
    }

    public ResponseViewMatch findByIdViewMatch(long matchId) {
        Match match = matchRepository.matchFindById(matchId);
        List<ResponseViewMatchUser> userList = matchRepository.getViewMatchUser(match);
        return new ResponseViewMatch(match, userList);
    }

}
