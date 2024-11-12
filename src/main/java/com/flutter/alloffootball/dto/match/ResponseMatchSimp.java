package com.flutter.alloffootball.dto.match;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ResponseMatchSimp {

    private final Long matchId;
    private final MatchStatus matchStatus;
    private final LocalDateTime matchDate;
    private final MatchData matchData;

    public ResponseMatchSimp(Match match) {
        this.matchId = match.getId();
        this.matchStatus = match.getMatchStatus();
        this.matchDate = match.getMatchDate();
        this.matchData = new MatchData(match);
    }
}
