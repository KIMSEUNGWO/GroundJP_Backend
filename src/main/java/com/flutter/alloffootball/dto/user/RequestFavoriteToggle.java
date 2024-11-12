package com.flutter.alloffootball.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFavoriteToggle {

    private Long fieldId;
    private boolean toggle;
}
