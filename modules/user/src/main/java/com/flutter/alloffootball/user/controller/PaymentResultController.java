package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.enums.CashType;
import com.flutter.alloffootball.user.dto.payment.ResponseApproveKakao;
import com.flutter.alloffootball.user.service.PaymentService;
import com.flutter.alloffootball.user.service.payment.KakaoPayService;
import com.flutter.alloffootball.user.service.payment.SocialPayCookieManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.flutter.alloffootball.user.dto.payment.KakaoPayConst.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/charge")
public class PaymentResultController {

    private final SocialPayCookieManager cookieManager;
    private final KakaoPayService kakaoPayService;
    private final PaymentService paymentService;

    @GetMapping("/kakao/completed")
    public String kakaoPayCompleted(@RequestParam(PG_TOKEN) String pg_token,
                                                      HttpServletResponse response,
                                                      @CookieValue(name = "userId") Long userId,
                                                      @CookieValue(name = TID) String tid,
                                                      @CookieValue(name = KAKAO_ORDER_ID) String partner_order_id,
                                                      @CookieValue(name = KAKAO_USER_ID) Long kakaoUserId) {

        log.info("결제승인 요청을 인증하는 토큰 : {}", pg_token);
        log.info("주문정보 : {}", partner_order_id);
        log.info("결재고유 번호 : {}", tid);

        ResponseApproveKakao approveKakao = kakaoPayService.payApprove(tid, pg_token, partner_order_id, userId);

        paymentService.receipt(userId, "카카오페이", CashType.CHARGE, approveKakao.getAmount().getTotal());

        cookieManager.deleteCookie(response);
        return "redirect:/api/charge/done";
    }
}
