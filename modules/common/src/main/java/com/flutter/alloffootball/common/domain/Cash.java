package com.flutter.alloffootball.common.domain;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.enums.CashType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "CASH")
public class Cash extends BaseEntityTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CASH_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    private CashType cashType;

    private String description;

    // 캐시 변화량
    private int cashUse;

    // 잔액
    private int cashNow;

}
