package com.flutter.alloffootball.admin.dto.user;

import com.flutter.alloffootball.common.domain.orders.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseUserOrder {

    private final long orderId;

    private final String title;
    private final long matchId;
    private final int price;
    private final LocalDateTime cancelDate;
    private final LocalDateTime createDate;

    public ResponseUserOrder(Order order) {
        this.orderId = order.getId();
        this.title = order.getMatch().getField().getTitle();
        this.matchId = order.getMatch().getId();
        this.price = order.getPrice();
        this.cancelDate = order.getCancelDate();
        this.createDate = order.getCreateDate();
    }
}
