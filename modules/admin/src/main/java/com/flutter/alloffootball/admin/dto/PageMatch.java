package com.flutter.alloffootball.admin.dto;

import com.flutter.alloffootball.admin.dto.match.RequestSearchMatch;
import com.flutter.alloffootball.common.dto.PageDto;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Getter
public class PageMatch<T> extends PageDto<T> {

    private final String word;
    private final Region region;
    private final SexType sex;
    private final MatchStatus state;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PageMatch(Page<T> page, RequestSearchMatch data) {
        super(page);
        this.word = data.getWord();
        this.region = data.getRegion();
        this.sex = data.getSex();
        this.state = data.getState();
        this.startDate = data.getStartDate();
        this.endDate = data.getEndDate();
    }
}
