package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.user.dto.refund.ResponseRefundResult;
import com.flutter.alloffootball.user.dto.order.RequestCancelOrder;

public interface RefundService {
    ResponseRefundResult cancelOrder(RequestCancelOrder cancelOrder, Long userId);
}
