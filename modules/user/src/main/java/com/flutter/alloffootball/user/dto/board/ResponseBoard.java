package com.flutter.alloffootball.user.dto.board;

import com.flutter.alloffootball.common.domain.board.Board;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.user.dto.user.ResponseBoardUser;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseBoard {

    private final Long boardId;
    private final String title;
    private final Region region;
    private final LocalDateTime createDate;

    private final ResponseBoardUser user;

    public ResponseBoard(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.region = board.getRegion();
        this.createDate = board.getCreateDate();
        this.user = new ResponseBoardUser(board.getUser());
    }
}
