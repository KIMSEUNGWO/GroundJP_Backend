package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.JwtUserContextHolder;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.payment.ReadyKakaoPayment;
import com.flutter.alloffootball.user.service.payment.KakaoPayService;
import com.flutter.alloffootball.user.service.payment.SocialPayCookieManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/cash/charge")
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final SocialPayCookieManager cookieManager;

    @GetMapping("/kakao")
    public ResponseEntity<Response> kakaoPayment(HttpServletResponse response,
                                                 @RequestParam("amount") int amount,
                                                 @JwtToken UserJwtToken userJwtToken) {
        cookieManager.deleteCookie(response);
        ReadyKakaoPayment payReady = kakaoPayService.payReady(userJwtToken.getUserId(), amount);
        System.out.println("payReady = " + payReady);
        return Response.ok(payReady);
    }
}
