package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.user.dto.match.ResponseMatchView;
import com.flutter.alloffootball.user.dto.order.RequestOrder;
import com.flutter.alloffootball.user.dto.order.ResponseOrderResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    ResponseOrderResult order(RequestOrder requestOrder, long userId, LocalDateTime now);

    Map<Integer, List<ResponseMatchView>> getHistory(LocalDateTime date, Long userId);

    List<ResponseMatchView> findAllByUserIdAndMatchDateAfter(Long userId, LocalDateTime now);

}
