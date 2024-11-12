package com.flutter.alloffootball.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchStatus {

    OPEN("모집중"),           // 모집중
    CLOSING_SOON("마감임박"),   // 마감임박
    CLOSED("마감"),         // 마감
    FINISHED("종료"),        // 경기종료
    ;

    private final String ko;

    public static String getKo(MatchStatus status) {
        if (status == null) return "상태전체";
        return status.getKo();
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
