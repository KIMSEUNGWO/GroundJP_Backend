package com.flutter.alloffootball.admin.dto.match;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ResponseSearchMatch {

    private final Long matchId;
    private final LocalDateTime matchDate;
    private final String region;
    private final String sex;
    private final String title;
    private final String person;
    private final String matchStatus;

    public ResponseSearchMatch(Match match, long currentPerson) {
        matchId = match.getId();
        matchDate = match.getMatchDate();
        region = match.getField().getAddress().getRegion().getKo();
        sex = SexType.getKo(match.getMatchSex());
        title = match.getField().getTitle();
        person = String.format("%d/%d", currentPerson, match.getMaxPerson());
        matchStatus = MatchStatus.getKo(match.getMatchStatus());
    }
}