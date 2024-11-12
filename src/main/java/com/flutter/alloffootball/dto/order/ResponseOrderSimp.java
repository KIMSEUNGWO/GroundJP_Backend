package com.flutter.alloffootball.dto.order;

import com.flutter.alloffootball.common.domain.field.Address;
import com.flutter.alloffootball.common.domain.match.Match;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseOrderSimp {

    private final String title;
    private final int totalPrice;
    private final int matchHour;

    private final Address address;
    private final LocalDateTime matchDate;

    public ResponseOrderSimp(Match match) {
        this.title = match.getField().getTitle();
        this.totalPrice = match.getPrice();
        this.matchHour = match.getMatchTime();

        this.matchDate = match.getMatchDate();
        this.address = match.getField().getAddress();
    }
}
