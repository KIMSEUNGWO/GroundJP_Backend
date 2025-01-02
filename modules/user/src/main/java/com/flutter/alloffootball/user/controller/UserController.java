package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
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
    public ResponseEntity<Response> profile(@JwtToken UserJwtToken userJwtToken) {
        ResponseUserProfile userProfile = userService.getUserProfile(userJwtToken.getUserId());
        return Response.ok(userProfile);
    }

    @PostMapping("/edit")
    public ResponseEntity<Response> getEdit(@ModelAttribute RequestEditUser editUser, @JwtToken UserJwtToken userJwtToken) {
        userService.editUser(userJwtToken.getUserId(), editUser);
        return Response.ok();
    }


    @GetMapping("/coupon")
    public ResponseEntity<Response> coupons(@JwtToken UserJwtToken userJwtToken) {
        List<ResponseCoupon> coupons = couponService.findAllByCouponsOnlyNotUse(userJwtToken.getUserId());
        return Response.ok(coupons);
    }

    @GetMapping("/history")
    public ResponseEntity<Response> calendar(@ModelAttribute RequestCalendar calendar, @JwtToken UserJwtToken userJwtToken) {
        Map<Integer, List<ResponseMatchView>> histories = orderService.getHistory(calendar.getDate(), userJwtToken.getUserId());
        return Response.ok(histories);
    }

    @GetMapping("/matches")
    public ResponseEntity<Response> matches(@JwtToken UserJwtToken userJwtToken) {
        List<ResponseMatchView> matchSoon = orderService.findAllByUserIdAndMatchDateAfter(userJwtToken.getUserId(), LocalDateTime.now());
        return Response.ok(matchSoon);
    }

    @GetMapping("/favorite")
    public ResponseEntity<Response> favorite(@JwtToken UserJwtToken userJwtToken) {
        List<ResponseFavorite> favorites = fieldService.findAllByFavorite(userJwtToken.getUserId());
        return Response.ok(favorites);
    }
    @PostMapping("/favorite")
    public ResponseEntity<Response> favoriteToggle(@RequestBody RequestFavoriteToggle favoriteToggle, @JwtToken UserJwtToken userJwtToken) {
        favoriteService.favoriteToggle(userJwtToken.getUserId(), favoriteToggle);
        return Response.ok();
    }

    @GetMapping("/distinct/nickname")
    public ResponseEntity<Response> distinctNickname(@RequestParam("nickname") String nickname) {
        boolean distinct = userService.distinctNickname(nickname);
        return Response.ok(distinct);
    }
}
