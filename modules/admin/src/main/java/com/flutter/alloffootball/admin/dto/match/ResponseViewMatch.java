package com.flutter.alloffootball.admin.dto.match;

import com.flutter.alloffootball.admin.dto.field.ResponseViewField;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ResponseViewMatch {

    private final long matchId;
    private final int personCount; // 6 vs 6 이면 6
    private final int matchCount; // 3파전이면 3
    private final int matchTime; // 2시간이면 2
    private final SexType sexType;
    private final int price;
    private final MatchStatus status;
    private final LocalDateTime matchDate;

    private final List<ResponseViewMatchUser> users;

    private final ResponseViewField field;

    public ResponseViewMatch(Match match, List<ResponseViewMatchUser> userList) {
        this.matchId = match.getId();
        this.personCount = match.getPersonCount();
        this.matchCount = match.getMatchCount();
        this.matchTime = match.getMatchTime();
        this.sexType = match.getMatchSex();
        this.price = match.getPrice();
        this.status = match.getMatchStatus();
        this.matchDate = match.getMatchDate();
        this.users = userList;
        this.field = new ResponseViewField(match.getField());
    }
}
