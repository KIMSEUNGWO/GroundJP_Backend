package com.flutter.alloffootball.dto.payment;

import static com.flutter.alloffootball.Constant.DOMAIN;

public abstract class KakaoPayConst {

    public static final String PG_TOKEN = "pg_token";

    // KAKAO PAY API 검증 시 사용되는 키 (TID)
    public static final String KAKAO_USER_ID = "partner_user_id";
    public static final String TID = "tid";
    public static final String KAKAO_ORDER_ID = "partner_order_id";

    public static final String APPROVAL_URI = DOMAIN + "/api/charge/kakao/completed";
    public static final String CANCEL_URI = DOMAIN + "/api/charge/cancel";
    public static final String FAIL_URI = DOMAIN + "/api/charge/fail";
}
