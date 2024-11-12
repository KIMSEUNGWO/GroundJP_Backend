package com.flutter.alloffootball.user.service.payment;

import com.flutter.alloffootball.user.api.ResponseTo;
import com.flutter.alloffootball.user.dto.payment.ReadyKakaoPayment;
import com.flutter.alloffootball.user.dto.payment.ResponseApproveKakao;
import com.flutter.alloffootball.user.repository.payment.KakaoPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final KakaoPayRepository kakaoPayRepository;
    private final ResponseTo responseTo;

    private final String PAY_READY_URI = "https://open-api.kakaopay.com/online/v1/payment/ready";
    private final String APPROVE_URI = "https://open-api.kakaopay.com/online/v1/payment/approve";

    @Value("${kakao.pay.secretKey}")
    private String SECRET_KEY;


    public ReadyKakaoPayment payReady(Long userId, int amount) {
        Map<String, String> body = kakaoPayRepository.getPayReadyBody(userId, amount);
        ReadyKakaoPayment post = responseTo.post(PAY_READY_URI, body, kakaoHttpHeader(), ReadyKakaoPayment.class);
        post.setPartner_order_id(body.get("partner_order_id"));
        post.setPartner_user_id(body.get("partner_user_id"));
        return post;
    }

    private HttpHeaders kakaoHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "SECRET_KEY " + SECRET_KEY);
        return headers;
    }

    public ResponseApproveKakao payApprove(String tid, String pgToken, String partnerOrderId, Long userId) {
        Map<String, String> body = kakaoPayRepository.getPayApproveBody(tid, pgToken, partnerOrderId, userId);
        return responseTo.post(APPROVE_URI, body, kakaoHttpHeader(), ResponseApproveKakao.class);
    }
}
