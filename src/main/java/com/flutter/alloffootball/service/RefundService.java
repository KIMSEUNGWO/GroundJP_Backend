package com.flutter.alloffootball.service;

import com.flutter.alloffootball.dto.order.RequestCancelOrder;
import com.flutter.alloffootball.dto.refund.ResponseRefundResult;

public interface RefundService {
    ResponseRefundResult cancelOrder(RequestCancelOrder cancelOrder, Long userId);
}
