package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.orders.CancelOrder;

public interface RefundRepository {
    void saveCancelOrder(CancelOrder saveCancelOrder);
}
