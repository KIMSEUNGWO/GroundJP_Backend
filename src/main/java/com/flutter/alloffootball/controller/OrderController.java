package com.flutter.alloffootball.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.dto.order.RequestCancelOrder;
import com.flutter.alloffootball.dto.order.RequestOrder;
import com.flutter.alloffootball.dto.order.ResponseOrderResult;
import com.flutter.alloffootball.dto.order.ResponseOrderSimp;
import com.flutter.alloffootball.service.MatchService;
import com.flutter.alloffootball.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<Response> order(@RequestBody RequestOrder requestOrder,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseOrderResult orderResult = orderService.order(requestOrder, userDetails.getUser().getId(), LocalDateTime.now());
        return Response.ok(orderResult);
    }

    /**
     * 경기 신청
     */
    @GetMapping("/match/{matchId}")
    public ResponseEntity<Response> orderGet(@PathVariable("matchId") long matchId,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseOrderSimp matchDetails = matchService.getOrderSimp(matchId, userDetails);
        return Response.ok(matchDetails);
    }


}
