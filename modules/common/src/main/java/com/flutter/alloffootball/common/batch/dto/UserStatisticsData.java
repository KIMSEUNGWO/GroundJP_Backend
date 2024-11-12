package com.flutter.alloffootball.common.batch.dto;

import lombok.Getter;

@Getter
public class UserStatisticsData {

    private final String key;
    private final long value;
    private final String color;

    public UserStatisticsData(String key, long value) {
        this.key = key;
        this.value = value;
        this.color = getColor(key);
    }

    private String getColor(String key) {
        return switch (key) {
            case "남자" -> "#4e73df";
            case "여자" -> "#ff4444";
            case "카카오" -> "#F3DA01";
            case "라인" -> "#08BF5B";
            case "애플" -> "#292929";
            case "10대" -> "#253331";
            case "20대" -> "#3E514E";
            case "30대" -> "#576F6B";
            case "40대" -> "#708D88";
            case "50대" -> "#89ABA5";
            default -> "#A2C9C2";
        };
    }
}
