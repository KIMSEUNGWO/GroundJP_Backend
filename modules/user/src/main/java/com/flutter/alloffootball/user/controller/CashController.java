package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.JwtUserContextHolder;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.cash.ResponseReceipt;
import com.flutter.alloffootball.user.service.CashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CashController {

    private final CashService cashService;

    @GetMapping("/receipt")
    public ResponseEntity<Response> receipt(@JwtToken UserJwtToken userJwtToken) {
        List<ResponseReceipt> receipts = cashService.getReceipts(userJwtToken.getUserId());
        return Response.ok(receipts);
    }

    @GetMapping("/cash")
    public ResponseEntity<Response> cash(@JwtToken UserJwtToken userJwtToken) {
        int cash = cashService.getCash(userJwtToken.getUserId());
        return Response.ok(cash);
    }


}
