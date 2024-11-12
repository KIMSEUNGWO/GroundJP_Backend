package com.flutter.alloffootball.dto.match;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.dto.field.ResponseFieldData;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseMatchDetails {

    private final Long matchId;
    private final LocalDateTime matchDate;
    private final SexType sexType;
    private final int person;
    private final int matchCount;
    private final int matchHour;
    private final int price;
    private final MatchStatus matchStatus;
    private final ResponseFieldData field;

    // 로그인한 회원인 경우, 이미 참가했는지 여부
    private final boolean alreadyJoin;

    private final RequestMatchStatistics statistics;

    public ResponseMatchDetails(Match match, boolean alreadyJoin, RequestMatchStatistics statistics) {
        this.matchId = match.getId();
        this.matchDate = match.getMatchDate();
        this.sexType = match.getMatchSex();
        this.person = match.getPersonCount();
        this.matchCount = match.getMatchCount();
        this.matchHour = match.getMatchTime();
        this.price = match.getPrice();
        this.matchStatus = match.getMatchStatus();
        this.field = new ResponseFieldData(match.getField());
        this.alreadyJoin = alreadyJoin;
        this.statistics = statistics;
    }
}
