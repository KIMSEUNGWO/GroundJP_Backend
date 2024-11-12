package com.flutter.alloffootball.service;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.dto.match.ResponseMatchView;
import com.flutter.alloffootball.dto.order.RequestCancelOrder;
import com.flutter.alloffootball.dto.order.RequestOrder;
import com.flutter.alloffootball.dto.order.ResponseOrderResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    ResponseOrderResult order(RequestOrder requestOrder, long userId, LocalDateTime now);

    Map<Integer, List<ResponseMatchView>> getHistory(LocalDateTime date, User user);

    List<ResponseMatchView> findAllByUserIdAndMatchDateAfter(Long userId, LocalDateTime now);

}
