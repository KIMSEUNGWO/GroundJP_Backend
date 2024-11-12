package com.flutter.alloffootball.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Provider {

    LINE,
    KAKAO,
    APPLE;

    @JsonCreator
    public static Provider from(String value) {
        return Provider.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
