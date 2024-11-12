package com.flutter.alloffootball.controller;

import com.flutter.alloffootball.common.component.UserDetailsUtil;
import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.dto.match.ResponseMatchDetails;
import com.flutter.alloffootball.dto.order.ResponseOrderSimp;
import com.flutter.alloffootball.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;
    private final UserDetailsUtil userDetailsUtil;

    /** 완료
     * 경기 상세정보 조회 ( 권한 필요없음 )
     */
    @GetMapping("/{matchId}")
    public ResponseEntity<Response> getMatchDetails(@PathVariable("matchId") long matchId, HttpServletRequest request) {
        CustomUserDetails userDetails = userDetailsUtil.getUserDetails(request);
        ResponseMatchDetails matchDetails = matchService.getMatchDetails(matchId, userDetails);
        return Response.ok(matchDetails);
    }


}
