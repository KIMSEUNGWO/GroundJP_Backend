package com.flutter.alloffootball.common.domain.match;

import com.flutter.alloffootball.common.domain.orders.Order;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MATCHES")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCHES_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIELD_ID")
    private Field field;

    private LocalDateTime matchDate;
    private int matchTime;

    @Enumerated(EnumType.STRING)
    private SexType matchSex;

    // 경기 수 2 : 2파전, 3 : 3파전
    private int matchCount;

    // 6:6이면 6, 5:5이면 5
    private int personCount;

    private int price;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    @Builder.Default
    @OneToMany(mappedBy = "match", orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

    public int getMaxPerson() {
        return matchCount * personCount;
    }

    public void refreshEnabledOrder(long enabledCount) {
        int maxPerson = getMaxPerson();
        if (maxPerson <= enabledCount) {
            // 최대 인원까지 찬 경우 마감으로 상태 변경
            matchStatus = MatchStatus.CLOSED;
        } else if (Math.ceil(0.8 * maxPerson) <= enabledCount) {
            // 80% 가 채워질 경우 마감임박으로 상태변경
            matchStatus = MatchStatus.CLOSING_SOON;
        } else {
            matchStatus = MatchStatus.OPEN;
        }
    }
}
