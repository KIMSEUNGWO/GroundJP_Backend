package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.user.dto.match.*;
import com.flutter.alloffootball.user.dto.order.ResponseOrderSimp;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchService {
    List<ResponseMatchView> search(RequestSearchMatch searchMatch, Pageable pageable);

    List<ResponseMatchSimp> findAllByFieldIdToMatchData(long fieldId, Pageable pageable);

    ResponseMatchDetails getMatchDetails(long matchId, CustomUserDetails userDetails);

    ResponseOrderSimp getOrderSimp(long matchId, CustomUserDetails userDetails);
}
