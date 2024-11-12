package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.orders.CancelOrder;

public interface RefundRepository {
    void saveCancelOrder(CancelOrder saveCancelOrder);
}
