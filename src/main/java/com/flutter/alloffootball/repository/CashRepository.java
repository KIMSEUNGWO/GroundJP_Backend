package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.Cash;

import java.util.List;

public interface CashRepository {

    List<Cash> findAllByReceipt(Long userId);
}
