package com.flutter.alloffootball.user.service.payment;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import static com.flutter.alloffootball.user.dto.payment.KakaoPayConst.*;

@Component
public class SocialPayCookieManager {

    public void deleteCookie(HttpServletResponse response) {
        Cookie user = new Cookie("userId", null);
        Cookie tid = new Cookie(TID, null);
        Cookie order = new Cookie(KAKAO_ORDER_ID, null);
        Cookie kakaoUser = new Cookie(KAKAO_USER_ID, null);

        user.setMaxAge(0); user.setPath("/");
        tid.setMaxAge(0); tid.setPath("/");
        order.setMaxAge(0); order.setPath("/");
        kakaoUser.setMaxAge(0); kakaoUser.setPath("/");

        response.addCookie(user);
        response.addCookie(tid);
        response.addCookie(order);
        response.addCookie(kakaoUser);
    }
}
