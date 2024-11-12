package com.flutter.alloffootball.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class InvalidData {

    private String field;
    private String message;
}
