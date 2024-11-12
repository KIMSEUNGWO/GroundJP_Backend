package com.flutter.alloffootball.common.batch.service;

import com.flutter.alloffootball.common.batch.MatchStatisticsEntity;
import com.flutter.alloffootball.common.jparepository.JpaFieldRepository;
import com.flutter.alloffootball.common.jparepository.JpaMatchRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchStatisticsService {

    private final JpaFieldRepository jpaFieldRepository;
    private final JpaMatchRepository jpaMatchRepository;

    @Getter
    private final MatchStatisticsEntity matchStatisticsEntity;

    public void refresh() {
        matchStatisticsEntity.setCreatedFieldCnt(jpaFieldRepository.count());
        matchStatisticsEntity.setMatch(jpaMatchRepository.findAll());
    }
}
