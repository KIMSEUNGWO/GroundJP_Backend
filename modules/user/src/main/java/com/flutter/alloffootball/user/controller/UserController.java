package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.dto.coupon.ResponseCoupon;
import com.flutter.alloffootball.user.dto.field.ResponseFavorite;
import com.flutter.alloffootball.user.dto.match.ResponseMatchView;
import com.flutter.alloffootball.user.dto.user.RequestCalendar;
import com.flutter.alloffootball.user.dto.user.RequestEditUser;
import com.flutter.alloffootball.user.dto.user.RequestFavoriteToggle;
import com.flutter.alloffootball.user.dto.user.ResponseUserProfile;
import com.flutter.alloffootball.user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final CouponService couponService;
    private final UserService userService;
    private final FieldService fieldService;
    private final OrderService orderService;
    private final FavoriteService favoriteService;

    @GetMapping("/profile")
    public ResponseEntity<Response> profile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseUserProfile userProfile = userService.getUserProfile(userDetails.getUser().getId());
        return Response.ok(userProfile);
    }

    @PostMapping("/edit")
    public ResponseEntity<Response> getEdit(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RequestEditUser editUser) {
        userService.editUser(userDetails.getUser().getId(), editUser);
        return Response.ok();
    }


    @GetMapping("/coupon")
    public ResponseEntity<Response> coupons(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ResponseCoupon> coupons = couponService.findAllByCouponsOnlyNotUse(userDetails.getUser().getId());
        return Response.ok(coupons);
    }

    @GetMapping("/history")
    public ResponseEntity<Response> calendar(@ModelAttribute RequestCalendar calendar, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<Integer, List<ResponseMatchView>> histories = orderService.getHistory(calendar.getDate(), userDetails.getUser());
        return Response.ok(histories);
    }

    @GetMapping("/matches")
    public ResponseEntity<Response> matches(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ResponseMatchView> matchSoon = orderService.findAllByUserIdAndMatchDateAfter(userDetails.getUser().getId(), LocalDateTime.now());
        return Response.ok(matchSoon);
    }

    @GetMapping("/favorite")
    public ResponseEntity<Response> favorite(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ResponseFavorite> favorites = fieldService.findAllByFavorite(userDetails.getUser().getId());
        return Response.ok(favorites);
    }
    @PostMapping("/favorite")
    public ResponseEntity<Response> favoriteToggle(@RequestBody RequestFavoriteToggle favoriteToggle, @AuthenticationPrincipal CustomUserDetails userDetails) {
        favoriteService.favoriteToggle(userDetails.getUser().getId(), favoriteToggle);
        return Response.ok();
    }

    @GetMapping("/distinct/nickname")
    public ResponseEntity<Response> distinctNickname(@RequestParam("nickname") String nickname) {
        boolean distinct = userService.distinctNickname(nickname);
        return Response.ok(distinct);
    }
}
