package com.flutter.alloffootball.common.exceptionhandler;

import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.common.dto.ResponseData;
import com.flutter.alloffootball.common.exception.DefaultError;
import com.flutter.alloffootball.common.exception.BindingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class BindingExceptionHandler {

    @ExceptionHandler(BindingException.class)
    public ResponseEntity<Response> handleBindingException(BindingException e) {
        e.printStackTrace();
        log.error("BindingException 예외 발생 !!! : {}", DefaultError.INVALID_DATA);
        List<String> fieldNames = getFieldNames(e.getBindingResult());
        return ResponseEntity.badRequest().body(new ResponseData<>(DefaultError.INVALID_DATA, fieldNames));
    }

    private List<String> getFieldNames(BindingResult bindingResult) {
        return bindingResult
            .getFieldErrors()
            .stream()
            .map(FieldError::getField)
            .toList();
    }
}
