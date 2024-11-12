package com.flutter.alloffootball.common.enums.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Parking {

    FREE("무료주차"),
    PAID("유료주차"),
    NOT_AVAILABLE("주차불가");

    private final String ko;

    @JsonCreator
    public static Parking fromJson(String value) {
        return Parking.valueOf(value.toUpperCase());
    }
}
