package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.Cash;
import com.flutter.alloffootball.common.jparepository.JpaCashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CashRepositoryImpl implements CashRepository {

    private final JpaCashRepository jpaCashRepository;

    @Override
    public List<Cash> findAllByReceipt(Long userId) {
        return jpaCashRepository.findAllByUser_IdOrderByCreateDateDesc(userId);
    }

}
