package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.field.ResponseFieldData;
import com.flutter.alloffootball.user.dto.match.ResponseMatchSimp;
import com.flutter.alloffootball.user.service.FieldService;
import com.flutter.alloffootball.user.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/field")
public class FieldController {

    private final MatchService matchService;
    private final FieldService fieldService;

    /**
     * 구장 상세정보 조회 ( 권한 필요없음 )
     */
    @GetMapping("/{fieldId}")
    public ResponseEntity<Response> fieldDetails(@PathVariable("fieldId") long fieldId, HttpServletRequest request, @JwtToken UserJwtToken userJwtToken) {
//        CustomUserDetails userDetails = userDetailsUtil.getUserDetails(request);
        ResponseFieldData fieldData = fieldService.getFieldDetails(fieldId, userJwtToken.getUserId());
        return Response.ok(fieldData);
    }

    /** 완료!
     * 구장별 경기목록 조회 ( 권한 필요없음 )
     *
     * TODO ResponseMatchSimp 안에 MatchData 안에 Region을 안쓰던데 뭔지 확인해야 함
     */
    @GetMapping("/{fieldId}/schedule")
    public ResponseEntity<Response> fieldSchedule(@PathVariable("fieldId") long fieldId, Pageable pageable) {
        PageRequest sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "matchDate");
        System.out.println("sortedPageable = " + sortedPageable);
        List<ResponseMatchSimp> matchDataList = matchService.findAllByFieldIdToMatchData(fieldId, sortedPageable);
        System.out.println("matchDataList.size() = " + matchDataList.size());
        return Response.ok(matchDataList);
    }
}
