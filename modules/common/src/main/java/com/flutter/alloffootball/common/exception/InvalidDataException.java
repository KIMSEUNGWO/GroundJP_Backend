package com.flutter.alloffootball.common.exception;

import com.flutter.alloffootball.common.dto.InvalidData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidDataException extends RuntimeException {

    private final InvalidData data;

}
