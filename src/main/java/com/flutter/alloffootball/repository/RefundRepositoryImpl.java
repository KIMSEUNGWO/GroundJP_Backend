package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.orders.CancelOrder;
import com.flutter.alloffootball.common.jparepository.JpaCancelOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    private final JpaCancelOrderRepository jpaCancelOrderRepository;

    @Override
    public void saveCancelOrder(CancelOrder saveCancelOrder) {
        jpaCancelOrderRepository.save(saveCancelOrder);
    }
}
