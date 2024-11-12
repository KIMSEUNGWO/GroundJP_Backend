package com.flutter.alloffootball.dto.field;

import com.flutter.alloffootball.common.domain.Favorite;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;

@Getter
public class ResponseFavorite {

    private final long fieldId;
    private final String title;
    private final Region region;
    private final String address;

    public ResponseFavorite(Favorite favorite) {
        this.fieldId = favorite.getField().getId();
        this.title = favorite.getField().getTitle();
        this.region = favorite.getField().getAddress().getRegion();
        this.address = favorite.getField().getAddress().getAddress();
    }
}
