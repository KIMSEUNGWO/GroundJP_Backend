package com.flutter.alloffootball.common.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexType {

    MALE("남자만"),
    FEMALE("여자만");

    private final String ko;

    public static String getKo(SexType sexType) {
        if (sexType == null) return "남녀무관";
        return sexType.ko;
    }

    @JsonCreator
    public static SexType fromJson(String value) {
        return SexType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
