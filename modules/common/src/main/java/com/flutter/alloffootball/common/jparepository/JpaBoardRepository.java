package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {
}
