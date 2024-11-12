package com.flutter.alloffootball.admin.dto.field;

import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestSearchField {

    private String word = "";
    private int page = 1;
    private Region region;
}
