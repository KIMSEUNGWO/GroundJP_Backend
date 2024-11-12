package com.flutter.alloffootball.common.domain.coupon;

import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.BaseEntityTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_COUPON")
public class UserCoupon extends BaseEntityTime {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_COUPON_ID")
    private Long id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon;

    @Getter
    private LocalDateTime expireDate;
    private char couponUse;

    public boolean isUse() {
        return couponUse == 'Y';
    }
    public boolean isExpire(LocalDateTime now) {
        return expireDate.isAfter(now);
    }
    public void use() {
        couponUse = 'Y';
    }
    public void notUse() {
        couponUse = 'N';
    }

}
