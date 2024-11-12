package com.flutter.alloffootball.admin.dto.field;

import com.flutter.alloffootball.common.enums.field.Parking;
import com.flutter.alloffootball.common.enums.field.Shower;
import com.flutter.alloffootball.common.enums.field.Toilet;
import com.flutter.alloffootball.common.enums.region.Region;
import lombok.Getter;

@Getter
public class FieldOption {

    private final Region[] region = Region.values();
    private final Parking[] parking = Parking.values();
    private final Shower[] shower = Shower.values();
    private final Toilet[] toilet = Toilet.values();

}
