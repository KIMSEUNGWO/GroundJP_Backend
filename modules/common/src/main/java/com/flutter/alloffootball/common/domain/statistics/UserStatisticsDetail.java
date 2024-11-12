package com.flutter.alloffootball.common.domain.statistics;

import com.flutter.alloffootball.common.batch.dto.UserStatisticsCategory;
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
@Table(name = "USER_STATISTICS_DETAIL")
public class UserStatisticsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_STATISTICS_DETAIL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_STATISTICS_ID")
    private UserStatistics userStatistics;

    @Enumerated(EnumType.STRING)
    private UserStatisticsCategory category;

    @Column(name = "STAT_KEY")
    private String key;
    private long value;
}
