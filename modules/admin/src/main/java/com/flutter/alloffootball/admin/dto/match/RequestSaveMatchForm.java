package com.flutter.alloffootball.admin.dto.match;

import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class RequestSaveMatchForm {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate matchDate;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime matchHour;
    private int matchTime;
    private int matchPerson;
    private int matchCount;
    private SexType sex;
    private int price;

}
