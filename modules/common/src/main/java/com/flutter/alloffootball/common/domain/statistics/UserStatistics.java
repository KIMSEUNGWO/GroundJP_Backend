package com.flutter.alloffootball.common.domain.statistics;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "USER_STATISTICS")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_STATISTICS_ID")
    private Long id;

    private LocalDate statsDate;
    private long userCnt;
    private long delta; // 유저 수 증감량

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "userStatistics", cascade = CascadeType.ALL)
    private List<UserStatisticsDetail> userStatisticsDetails = new ArrayList<>();


}
