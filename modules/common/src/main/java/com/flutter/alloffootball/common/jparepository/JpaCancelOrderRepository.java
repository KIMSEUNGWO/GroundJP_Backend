package com.flutter.alloffootball.common.jparepository;

import com.flutter.alloffootball.common.domain.orders.CancelOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCancelOrderRepository extends JpaRepository<CancelOrder, Long> {
}
