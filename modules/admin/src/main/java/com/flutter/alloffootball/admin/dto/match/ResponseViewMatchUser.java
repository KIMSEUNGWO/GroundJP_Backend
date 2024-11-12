package com.flutter.alloffootball.admin.dto.match;

import com.flutter.alloffootball.common.domain.orders.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseViewMatchUser {

    private final long userId;
    private final String nickname;
    private final int price; // 결제금액
    private final LocalDateTime createDate;

    public ResponseViewMatchUser(Order order) {
        this.userId = order.getUser().getId();
        this.nickname = order.getUser().getNickname();
        this.price = order.getPrice();
        this.createDate = order.getCreateDate();
    }
}
