package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
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
    public ResponseEntity<Response> cancelOrder(@RequestBody RequestCancelOrder cancelOrder,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseRefundResult refundResult = refundService.cancelOrder(cancelOrder, userDetails.getUser().getId());
        return Response.ok(refundResult);
    }
}
