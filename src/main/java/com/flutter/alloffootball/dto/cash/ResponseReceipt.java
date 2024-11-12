package com.flutter.alloffootball.dto.cash;

import com.flutter.alloffootball.common.domain.Cash;
import com.flutter.alloffootball.common.enums.CashType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseReceipt {

    private final String title;
    private final CashType type;
    private final LocalDateTime date;
    private final int useCash;
    private final int remainCash;

    public ResponseReceipt(Cash cash) {
        title = cash.getDescription();
        type = cash.getCashType();
        date = cash.getCreateDate();
        useCash = cash.getCashUse();
        remainCash = cash.getCashNow();
    }
}
