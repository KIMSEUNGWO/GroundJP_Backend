package com.flutter.alloffootball.common.domain.orders;

import com.flutter.alloffootball.common.domain.BaseEntityTime;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "CANCEL_ORDERS")
public class CancelOrder extends BaseEntityTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CANCEL_ORDERS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHES_ID")
    private Match match;

    @Builder.Default
    private int refundAmount = 0;

}
