package com.flutter.alloffootball.admin.dto.match;

import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RequestSearchMatch {

    private Region region;
    private SexType sex;
    private MatchStatus state;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    private String word = "";
    private int page = 1;
}
