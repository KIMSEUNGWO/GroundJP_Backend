package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.match.ResponseMatchDetails;
import com.flutter.alloffootball.user.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    /** 완료
     * 경기 상세정보 조회 ( 권한 필요없음 )
     */
    @GetMapping("/{matchId}")
    public ResponseEntity<Response> getMatchDetails(@PathVariable("matchId") long matchId, @JwtToken UserJwtToken userJwtToken) {
        ResponseMatchDetails matchDetails = matchService.getMatchDetails(matchId, userJwtToken.getUserId());
        return Response.ok(matchDetails);
    }


}
