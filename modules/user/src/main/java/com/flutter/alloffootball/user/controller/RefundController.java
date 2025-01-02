package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.JwtUserContextHolder;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.order.RequestCancelOrder;
import com.flutter.alloffootball.user.dto.refund.ResponseRefundResult;
import com.flutter.alloffootball.user.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cancel")
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<Response> cancelOrder(@RequestBody RequestCancelOrder cancelOrder, @JwtToken UserJwtToken userJwtToken) {
        ResponseRefundResult refundResult = refundService.cancelOrder(cancelOrder, userJwtToken.getUserId());
        return Response.ok(refundResult);
    }
}
