package com.flutter.alloffootball.dto.match;

import com.flutter.alloffootball.common.enums.SexType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class RequestMatchStatistics {

    private Map<SexType, Integer> sexStatistics;
}
