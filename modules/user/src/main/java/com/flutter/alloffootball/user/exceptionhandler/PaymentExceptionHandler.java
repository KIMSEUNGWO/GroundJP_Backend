package com.flutter.alloffootball.user.exceptionhandler;

import com.flutter.alloffootball.common.exception.UserException;
import com.flutter.alloffootball.user.controller.PaymentResultController;
import com.flutter.alloffootball.user.service.payment.SocialPayCookieManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice(assignableTypes = PaymentResultController.class)
@RequiredArgsConstructor
public class PaymentExceptionHandler {

    private final SocialPayCookieManager cookieManager;

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> serverError(HttpServletResponse response) {
        log.error("서버 에러");
        cookieManager.deleteCookie(response);
        return ResponseEntity.badRequest().build();
    }

    // 쿠키 데이터가 null인경우, userId가 Number 가 아닌 경우
    @ExceptionHandler({MissingRequestCookieException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleMissingRequestCookieException(HttpServletResponse response) {
        log.error("결제 요청 데이터 에러! ");
        cookieManager.deleteCookie(response);
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> userNotExistsException(HttpServletResponse response) {
        log.error("결제 요청 데이터 에러! 사용자를 찾을 수 없음");
        cookieManager.deleteCookie(response);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
