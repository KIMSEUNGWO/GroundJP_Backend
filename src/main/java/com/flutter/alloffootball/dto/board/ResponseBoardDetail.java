package com.flutter.alloffootball.dto.board;

import com.flutter.alloffootball.common.domain.board.Board;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.dto.match.ResponseMatchView;
import com.flutter.alloffootball.dto.user.ResponseBoardUser;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseBoardDetail {

    private final Long boardId;
    private final String title;
    private final String content;

    private final Region region;
    private final LocalDateTime createDate;
    private final boolean isUpdated;

    private final ResponseBoardUser user;
    private final ResponseMatchView match;

    public ResponseBoardDetail(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();

        this.region = board.getRegion();
        this.createDate = board.getCreateDate();
        this.isUpdated = board.getUpdateDate() != null;

        this.user = new ResponseBoardUser(board.getUser());
        this.match = board.getMatch() == null ? null : new ResponseMatchView(board.getMatch());
    }
}
