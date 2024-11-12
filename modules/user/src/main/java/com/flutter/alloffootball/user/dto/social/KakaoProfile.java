package com.flutter.alloffootball.user.dto.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
public class KakaoProfile {

    private long id;
    private LocalDateTime connected_at;
    private Map<String, Object> properties;
}
