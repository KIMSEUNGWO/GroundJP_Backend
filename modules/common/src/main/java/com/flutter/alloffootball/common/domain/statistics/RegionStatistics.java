package com.flutter.alloffootball.common.domain.statistics;

import com.flutter.alloffootball.common.enums.region.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "REGION_STATISTICS")
public class RegionStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGION_STATISTICS_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Region region;

    private long completeCnt;
    private long cancelCnt;

    private LocalDate statsDate;
}
