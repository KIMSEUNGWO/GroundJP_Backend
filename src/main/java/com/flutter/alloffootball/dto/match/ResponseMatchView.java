package com.flutter.alloffootball.dto.match;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseMatchView {

    private final Long matchId;
    private final MatchStatus matchStatus;
    private final String title;
    private final LocalDateTime matchDate;
    private final MatchData matchData;

    public ResponseMatchView(Match match) {
        this.matchId = match.getId();
        this.matchStatus = match.getMatchStatus();
        this.title = match.getField().getTitle();
        this.matchDate = match.getMatchDate();
        this.matchData = new MatchData(match);
    }
}
