package com.flutter.alloffootball.common.domain.orders;

import com.flutter.alloffootball.common.domain.BaseEntityTime;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "ORDERS")
public class Order extends BaseEntityTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHES_ID")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime cancelDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_COUPON_ID")
    private UserCoupon userCoupon;

    // 결제 금액
    private int price;

    public void setCancel() {
        orderStatus = OrderStatus.CANCEL;
        cancelDate = LocalDateTime.now();
    }

}
