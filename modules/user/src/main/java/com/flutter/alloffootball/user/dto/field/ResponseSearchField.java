package com.flutter.alloffootball.user.dto.field;

import com.flutter.alloffootball.common.domain.field.Field;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseSearchField {

    private final Long fieldId;
    private final String region;
    private final String title;
    private final String address;

    public ResponseSearchField(Field field) {
        this.fieldId = field.getId();
        this.title = field.getTitle();
        this.region = field.getAddress().getRegion().getKo();
        this.address = field.getAddress().getAddress();
    }
}
