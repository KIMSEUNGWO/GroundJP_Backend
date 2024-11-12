package com.flutter.alloffootball.dto.board;

import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class RequestCreateBoard {

    @Length(min = 2, max = 50)
    private String title;
    @Length(max = 300)
    private String content;
    private Long matchId;
    private Region region;
}
