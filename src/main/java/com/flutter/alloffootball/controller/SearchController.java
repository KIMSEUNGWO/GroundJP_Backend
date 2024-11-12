package com.flutter.alloffootball.controller;

import com.flutter.alloffootball.admin.dto.field.ResponseSearchField;
import com.flutter.alloffootball.admin.dto.notice.RequestSearchNotice;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.dto.board.RequestSearchBoard;
import com.flutter.alloffootball.dto.board.ResponseBoard;
import com.flutter.alloffootball.dto.match.RequestSearchMatch;
import com.flutter.alloffootball.dto.match.ResponseMatchView;
import com.flutter.alloffootball.dto.notice.RequestNotice;
import com.flutter.alloffootball.service.BoardService;
import com.flutter.alloffootball.service.FieldService;
import com.flutter.alloffootball.service.MatchService;
import com.flutter.alloffootball.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final MatchService matchService;
    private final FieldService fieldService;
    private final BoardService boardService;
    private final NoticeService noticeService;

    /** 완료
     * 조건에 맞는 경기 목록 조회 ( 권한 필요없음 )
     */
    @GetMapping("/match")
    public ResponseEntity<Response> getMatchList(@ModelAttribute RequestSearchMatch searchMatch,
                                                 Pageable pageable) {
        System.out.println("searchMatch = " + searchMatch);
        System.out.println("pageable = " + pageable);
        List<ResponseMatchView> matchList = matchService.search(searchMatch, pageable);
        return Response.ok(matchList);
    }

    @GetMapping("/field")
    public ResponseEntity<Response> searchField(@RequestParam("word") String word) {
        List<ResponseSearchField> fields = fieldService.search(word);
        return Response.ok(fields);
    }

    @GetMapping("/board")
    public ResponseEntity<Response> searchBoard(@ModelAttribute RequestSearchBoard searchBoard,
                                                Pageable pageable) {
        System.out.println("searchBoard = " + searchBoard);
        List<ResponseBoard> boards = boardService.search(searchBoard, pageable);
        return Response.ok(boards);
    }

    @GetMapping("/notice")
    public ResponseEntity<Response> searchNotice(@ModelAttribute RequestSearchNotice searchNotice, Pageable pageable) {
        System.out.println("searchNotice = " + searchNotice);
        List<RequestNotice> notices = noticeService.findAllBySearchNotice(pageable).getContent();
        return Response.ok(notices);
    }


}
