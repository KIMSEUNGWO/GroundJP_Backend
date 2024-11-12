package com.flutter.alloffootball.user.dto.board;

import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestSearchBoard {

    private Region region;
}
