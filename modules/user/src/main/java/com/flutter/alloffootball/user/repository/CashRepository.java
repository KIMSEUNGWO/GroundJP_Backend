package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.Cash;

import java.util.List;

public interface CashRepository {

    List<Cash> findAllByReceipt(Long userId);
}
