package com.flutter.alloffootball.user.dto.match;

import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;

@Getter
public class MatchData {

    private final SexType sex;
    private final Region region;
    private final int person;
    private final int matchCount;

    public MatchData(Match match) {
        this.sex = match.getMatchSex();
        this.region = match.getField().getAddress().getRegion();
        this.person = match.getPersonCount();
        this.matchCount = match.getMatchCount();
    }
}
