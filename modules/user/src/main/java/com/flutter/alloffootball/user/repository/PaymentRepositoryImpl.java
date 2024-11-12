package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.Cash;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.CashType;
import com.flutter.alloffootball.common.jparepository.JpaCashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaCashRepository jpaCashRepository;

    @Override
    public void receipt(User user, String message, CashType cashType, int amount) {
        user.receipt(cashType, amount);
        Cash saveCash = Cash.builder()
            .description(message)
            .user(user)
            .cashType(cashType)
            .cashUse(cashType.accept(amount))
            .cashNow(user.getCash())
            .build();

        jpaCashRepository.save(saveCash);
    }
}
