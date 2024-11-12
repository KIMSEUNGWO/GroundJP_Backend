package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.user.dto.cash.ResponseReceipt;
import com.flutter.alloffootball.user.repository.CashRepository;
import com.flutter.alloffootball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {

    private final UserRepository userRepository;
    private final CashRepository cashRepository;


    @Override
    public List<ResponseReceipt> getReceipts(User user) {
        return cashRepository.findAllByReceipt(user.getId())
            .stream()
            .map(ResponseReceipt::new)
            .toList();
    }

    @Override
    public int getCash(User user) {
        return userRepository.findById(user.getId()).getCash();
    }
}
