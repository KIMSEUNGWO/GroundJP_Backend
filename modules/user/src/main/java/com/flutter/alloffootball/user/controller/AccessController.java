package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccessController {

    @GetMapping("/accessToken")
    public ResponseEntity<Response> checkAccessToken() {
        System.out.println("MainController.checkAccessToken");
        return Response.ok();
    }

}
