package com.flutter.alloffootball.common.dto;

import com.flutter.alloffootball.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class Response {

    private final String result;

    public Response(ErrorCode errorCode) {
        this.result = errorCode.getErrorCode();
    }

    private static final Response defaultResponse = new Response(() -> "OK");

    public static ResponseEntity<Response> ok() {
        return ResponseEntity.ok(defaultResponse);
    }
    public static ResponseEntity<Response> ok(ErrorCode errorCode) {
        return ResponseEntity.ok(new Response(errorCode));
    }
    public static <T> ResponseEntity<Response> ok(T data) {
        return ResponseEntity.ok(new ResponseData<>(() -> "OK", data));
    }
}
