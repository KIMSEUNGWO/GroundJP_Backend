package com.flutter.alloffootball.admin.dto.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RequestDateRange {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

}
