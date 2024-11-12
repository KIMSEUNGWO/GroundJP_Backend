package com.flutter.alloffootball.user.dto.payment;

import lombok.*;

@Getter
@Setter
@ToString
public class ReadyKakaoPayment {

    private String tid;
    private String next_redirect_pc_url;
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String android_app_scheme;
    private String ios_app_scheme;

    private String partner_order_id;
    private String partner_user_id;
}