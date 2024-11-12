package com.flutter.alloffootball.common.batch.dto;

import com.flutter.alloffootball.common.domain.statistics.RegionStatistics;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;

@Getter
public class ResponseRegionStatistics {

    private final String region;
    private final long completeCnt;
    private final long cancelCnt;

    public ResponseRegionStatistics(RegionStatistics regionStatistics) {
        this.region = regionStatistics.getRegion().getKo();
        this.completeCnt = regionStatistics.getCompleteCnt();
        this.cancelCnt = regionStatistics.getCancelCnt();
    }
}
