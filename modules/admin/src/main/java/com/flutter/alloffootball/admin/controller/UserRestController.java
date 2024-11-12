package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.PageUser;
import com.flutter.alloffootball.admin.dto.user.RequestSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseUserOrder;
import com.flutter.alloffootball.admin.service.PageService;
import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.PageDto;
import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserRestController {

    private final PageService pageService;

    @GetMapping("/get")
    public ResponseEntity<Response> userList(@ModelAttribute RequestSearchUser data) {
        System.out.println("data = " + data);
        Pageable pageable = PageRequest.of(data.getPage() - 1, 10);
        return Response.ok(new PageUser<>(pageService.findAllBySearchUser(data, pageable), data));
    }

    @GetMapping("/order/get")
    public ResponseEntity<Response> userOrderList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestParam(name = "page", defaultValue = "1") int page) {
        System.out.println("page = " + page);
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<ResponseUserOrder> result = pageService.findAllByUserOrder(userDetails.getUser().getId(), pageable);
        return Response.ok(new PageDto<>(result));
    }
}
