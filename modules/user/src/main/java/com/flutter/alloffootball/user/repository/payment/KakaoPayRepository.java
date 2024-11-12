package com.flutter.alloffootball.user.repository.payment;

import com.flutter.alloffootball.user.dto.payment.KakaoPayConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class KakaoPayRepository {

    // TC0ONETIME = 테스트 코드
    private static final String CID_CODE = "TC0ONETIME";


    private Map<String, String> initBody(Long userId, String partner_order_id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", CID_CODE);
        parameters.put("partner_order_id", partner_order_id); // 주문명
        parameters.put("partner_user_id", String.valueOf(userId));
        return parameters;
    }

    public String generatePartnerOrderId(Long userId) {
        return "app_ground_jp_" + userId;
    }


    public Map<String, String> getPayReadyBody(Long userId, int amount) {
        Map<String, String> parameters = initBody(userId, generatePartnerOrderId(userId));
        parameters.put("item_name", "캐시충전");
        parameters.put("quantity", String.valueOf(1));
        parameters.put("total_amount", String.valueOf(amount));
        parameters.put("tax_free_amount", "0");
        parameters.put("approval_url", KakaoPayConst.APPROVAL_URI); // 결제승인시 넘어갈 url
        parameters.put("cancel_url", KakaoPayConst.CANCEL_URI); // 결제취소시 넘어갈 url
        parameters.put("fail_url", KakaoPayConst.FAIL_URI); // 결제 실패시 넘어갈 url
        return parameters;
    }

    public Map<String, String> getPayApproveBody(String tid, String pgToken, String partner_order_id, Long memberId) {
        Map<String, String> parameters = initBody(memberId, partner_order_id);
        parameters.put("tid", tid);
        parameters.put("pg_token", pgToken);
        return parameters;
    }
}
