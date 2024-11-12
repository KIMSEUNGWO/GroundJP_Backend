package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.board.Board;
import com.flutter.alloffootball.common.exception.BoardError;
import com.flutter.alloffootball.common.exception.BoardException;
import com.flutter.alloffootball.common.jparepository.JpaBoardRepository;
import com.flutter.alloffootball.dto.board.RequestSearchBoard;
import com.flutter.alloffootball.querydsl.QueryDslBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final JpaBoardRepository jpaBoardRepository;
    private final QueryDslBoardRepository queryDslBoardRepository;

    public Board findById(Long boardId) {
        if (boardId == null) throw new BoardException(BoardError.BOARD_NOT_EXISTS);
        return jpaBoardRepository.findById(boardId)
            .orElseThrow(() -> new BoardException(BoardError.BOARD_NOT_EXISTS));
    }

    public List<Board> search(RequestSearchBoard searchBoard, Pageable pageable) {
        return queryDslBoardRepository.search(searchBoard, pageable);
    }

    public void save(Board saveBoard) {
        jpaBoardRepository.save(saveBoard);
    }

    public void delete(Board board) {
        jpaBoardRepository.delete(board);
    }
}
