package com.flutter.alloffootball.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@AllArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomRuntimeException extends RuntimeException {

    private final ErrorCode error;
}
