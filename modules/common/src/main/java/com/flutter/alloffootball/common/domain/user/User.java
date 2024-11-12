package com.flutter.alloffootball.common.domain.user;

import com.flutter.alloffootball.common.domain.BaseEntityTime;
import com.flutter.alloffootball.common.domain.Cash;
import com.flutter.alloffootball.common.domain.Favorite;
import com.flutter.alloffootball.common.domain.coupon.UserCoupon;
import com.flutter.alloffootball.common.domain.orders.CancelOrder;
import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.enums.CashType;
import com.flutter.alloffootball.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
@Builder
public class User extends BaseEntityTime {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String email;

    @Getter
    @Embedded
    private Social social;

    @Getter
    @Embedded
    private UserInfo userInfo;

    @Setter
    @Getter
    @Column(unique = true)
    private String nickname;

    @Getter
    private int cash;

    @Enumerated(EnumType.STRING) @Getter
    private Role role;

    @Getter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROFILE_ID")
    private Profile profile;

    @Getter
    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Favorite> favoriteList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Cash> receiptList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<UserCoupon> userCouponList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<CancelOrder> cancelOrderList = new ArrayList<>();

    public void receipt(CashType cashType, int receipt) {
        this.cash = Math.max(0, this.cash + cashType.accept(receipt));
    }

    public List<UserCoupon> possibleCouponList() {
        LocalDateTime now = LocalDateTime.now();
        return userCouponList
            .stream()
            .filter(userCoupon -> !userCoupon.isUse() && userCoupon.isExpire(now))
            .toList();
    }
}
