package com.flutter.alloffootball.service;

import com.flutter.alloffootball.dto.board.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    List<ResponseBoard> search(RequestSearchBoard searchBoard, Pageable pageable);

    ResponseBoardDetail findBoardDetail(Long boardId);

    void createBoard(RequestCreateBoard createBoard, Long userId);

    void editBoard(RequestEditBoard editBoard, Long userId);

    void deleteBoard(RequestDeleteBoard deleteBoard, Long userId);
}
