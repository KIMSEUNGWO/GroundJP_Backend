package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.JwtUserContextHolder;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.order.RequestCancelOrder;
import com.flutter.alloffootball.user.dto.order.RequestOrder;
import com.flutter.alloffootball.user.dto.order.ResponseOrderResult;
import com.flutter.alloffootball.user.dto.order.ResponseOrderSimp;
import com.flutter.alloffootball.user.service.MatchService;
import com.flutter.alloffootball.user.service.OrderService;
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
    public ResponseEntity<Response> order(@RequestBody RequestOrder requestOrder) {
        UserJwtToken userJwtToken = JwtUserContextHolder.getUserJwtToken();
        ResponseOrderResult orderResult = orderService.order(requestOrder, userJwtToken.getUserId(), LocalDateTime.now());
        return Response.ok(orderResult);
    }

    /**
     * 경기 신청
     */
    @GetMapping("/match/{matchId}")
    public ResponseEntity<Response> orderGet(@PathVariable("matchId") long matchId, @JwtToken UserJwtToken userJwtToken) {
        ResponseOrderSimp matchDetails = matchService.getOrderSimp(matchId, userJwtToken.getUserId());
        return Response.ok(matchDetails);
    }


}
