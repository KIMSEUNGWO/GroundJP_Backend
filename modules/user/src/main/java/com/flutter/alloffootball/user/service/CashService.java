package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.user.dto.cash.ResponseReceipt;

import java.util.List;

public interface CashService {
    List<ResponseReceipt> getReceipts(Long userId);

    int getCash(Long userId);
}
