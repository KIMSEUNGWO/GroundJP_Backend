package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCashRepository extends JpaRepository<Cash, Long> {

    List<Cash> findAllByUser_IdOrderByCreateDateDesc(Long userId);

    List<Cash> findAllByUser_Id(Long userId);
}
