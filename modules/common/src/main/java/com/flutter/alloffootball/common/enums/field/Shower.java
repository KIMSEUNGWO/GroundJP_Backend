package com.flutter.alloffootball.common.enums.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Shower {

    Y("샤워장 있음"),
    N("샤워장 없음");

    private final String ko;

    @JsonCreator
    public static Shower fromJson(String value) {
        return Shower.valueOf(value.toUpperCase());
    }
}
