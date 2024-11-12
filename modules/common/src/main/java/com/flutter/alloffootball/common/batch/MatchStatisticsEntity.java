package com.flutter.alloffootball.common.batch;

import com.flutter.alloffootball.common.domain.match.Match;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
@Getter
@ToString
public class MatchStatisticsEntity {

    private long openCnt;
    private long closingSoonCnt;
    private long closedCnt;

    private long createdFieldCnt;
    private long createdMatchCnt;

    public void setMatch(List<Match> matchAll) {
        createdMatchCnt = matchAll.size();

        matchAll.forEach(match -> {
            switch (match.getMatchStatus()) {
                case OPEN -> openCnt++;
                case CLOSING_SOON -> closingSoonCnt++;
                case CLOSED -> closedCnt++;
            }
        });
    }
}
