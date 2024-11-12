package com.flutter.alloffootball.common.exceptionhandler;

import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.common.dto.ResponseData;
import com.flutter.alloffootball.common.exception.BanWordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class WodInspectorExceptionHandler {

    @ExceptionHandler(BanWordException.class)
    public ResponseEntity<Response> banWordException(final BanWordException e) {
        log.error("BanWordException 발생");
        return ResponseEntity.badRequest().body(new ResponseData<>(() -> "BAN_WORD_INCLUDE", e.getWords()));
    }
}
