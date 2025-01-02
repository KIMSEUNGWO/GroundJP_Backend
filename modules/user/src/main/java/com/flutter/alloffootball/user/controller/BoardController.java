package com.flutter.alloffootball.user.controller;

import com.flutter.alloffootball.common.component.WordInspector;
import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import com.flutter.alloffootball.common.exception.BindingException;
import com.flutter.alloffootball.user.config.jwt.JwtUserContextHolder;
import com.flutter.alloffootball.user.config.jwt.UserJwtToken;
import com.flutter.alloffootball.user.config.jwt.annotataion.JwtToken;
import com.flutter.alloffootball.user.dto.board.RequestCreateBoard;
import com.flutter.alloffootball.user.dto.board.RequestDeleteBoard;
import com.flutter.alloffootball.user.dto.board.RequestEditBoard;
import com.flutter.alloffootball.user.dto.board.ResponseBoardDetail;
import com.flutter.alloffootball.user.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final WordInspector wordInspector;

    @GetMapping("/{boardId}")
    public ResponseEntity<Response> getBoardDetail(@PathVariable("boardId") Long boardId) {
        ResponseBoardDetail boardDetail = boardService.findBoardDetail(boardId);
        return Response.ok(boardDetail);
    }

    @PostMapping("/method")
    public ResponseEntity<Response> addBoard(@Validated @RequestBody RequestCreateBoard createBoard,
                                             BindingResult bindingResult,
                                             @JwtToken UserJwtToken userJwtToken) {
        System.out.println("createBoard = " + createBoard);
        if (bindingResult.hasErrors()) throw new BindingException(bindingResult);
        wordInspector.inspect(createBoard.getTitle(), createBoard.getContent());

        boardService.createBoard(createBoard, userJwtToken.getUserId());
        return Response.ok();
    }

    @PatchMapping("/method")
    public ResponseEntity<Response> editBoard(@Validated @RequestBody RequestEditBoard editBoard,
                                             BindingResult bindingResult,
                                              @JwtToken UserJwtToken userJwtToken) {
        if (bindingResult.hasErrors()) throw new BindingException(bindingResult);
        wordInspector.inspect(editBoard.getTitle(), editBoard.getContent());

        boardService.editBoard(editBoard, userJwtToken.getUserId());
        return Response.ok();
    }

    @DeleteMapping("/method")
    public ResponseEntity<Response> deleteBoard(@Validated @RequestBody RequestDeleteBoard deleteBoard, @JwtToken UserJwtToken userJwtToken) {
        boardService.deleteBoard(deleteBoard, userJwtToken.getUserId());
        return Response.ok();
    }

}
