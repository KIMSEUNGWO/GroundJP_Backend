package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.domain.board.Board;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.exception.CustomRuntimeException;
import com.flutter.alloffootball.common.exception.DefaultError;
import com.flutter.alloffootball.common.jparepository.JpaMatchRepository;
import com.flutter.alloffootball.user.dto.board.*;
import com.flutter.alloffootball.user.repository.BoardRepository;
import com.flutter.alloffootball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final JpaMatchRepository jpaMatchRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<ResponseBoard> search(RequestSearchBoard searchBoard, Pageable pageable) {
        return boardRepository.search(searchBoard, pageable).stream()
            .map(ResponseBoard::new)
            .toList();
    }

    @Override
    public ResponseBoardDetail findBoardDetail(Long boardId) {
        Board board = findByBoardId(boardId);
        return new ResponseBoardDetail(board);
    }

    @Override
    public void createBoard(RequestCreateBoard createBoard, Long userId) {
        User user = userRepository.findById(userId);

        // 게시글에 Match null 허용
        Match match = findMatchOptionally(createBoard.getMatchId());

        boardRepository.save(Board.builder()
            .user(user)
            .match(match)
            .title(createBoard.getTitle())
            .content(createBoard.getContent())
            .region(createBoard.getRegion())
            .build());
    }

    @Override
    public void editBoard(RequestEditBoard editBoard, Long userId) {
        Board board = findByBoardId(editBoard.getBoardId());

        // 자신의 게시물이 아닌 경우
        validIsOwner(userId, board);

        // 게시글에 Match null 허용
        Match match = findMatchOptionally(editBoard.getMatchId());

        board.update(editBoard.getTitle(), editBoard.getContent(), editBoard.getRegion(), match);
    }

    @Override
    public void deleteBoard(RequestDeleteBoard deleteBoard, Long userId) {
        Board board = findByBoardId(deleteBoard.getBoardId());

        // 자신의 게시물이 아닌 경우
        validIsOwner(userId, board);

        boardRepository.delete(board);
    }

    // 게시글에 Match null 허용
    private Match findMatchOptionally(Long matchId) {
        return Optional.ofNullable(matchId)
            .flatMap(jpaMatchRepository::findById)
            .orElse(null);
    }

    // 자신의 게시물이 아닌 경우 예외발생
    private void validIsOwner(Long userId, Board board) {
        if (!board.isOwner(userId)) throw new CustomRuntimeException(DefaultError.NOT_AUTHENTICATION);
    }

    private Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId);
    }
}
