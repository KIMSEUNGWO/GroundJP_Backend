package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.enums.CashType;

public interface PaymentService {
    void receipt(Long userId, String message, CashType cashType, int amount);
}