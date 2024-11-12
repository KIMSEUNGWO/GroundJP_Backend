package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.CashType;

public interface PaymentRepository {
    void receipt(User user, String message, CashType cashType, int amount);
}
