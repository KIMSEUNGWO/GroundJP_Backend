package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.match.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface JpaMatchRepository extends JpaRepository<Match, Long> {

    Page<Match> findAllByField_IdAndMatchDateAfter(long fieldId, Pageable pageable, LocalDateTime dateTime);
}
