package com.flutter.alloffootball.common.enums.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Toilet {

    Y("화장실 있음"),
    N("화장실 없음");

    private final String ko;

    @JsonCreator
    public static Toilet fromJson(String value) {
        return Toilet.valueOf(value.toUpperCase());
    }
}
