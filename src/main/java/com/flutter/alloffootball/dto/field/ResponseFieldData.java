package com.flutter.alloffootball.dto.field;

import com.flutter.alloffootball.common.domain.BaseEntityImage;
import com.flutter.alloffootball.common.domain.field.Address;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldData;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseFieldData {

    private final Long fieldId;
    private final String title;
    private final Address address;
    private final FieldData fieldData;
    private final String description;
    private final List<String> images;

    public ResponseFieldData(Field field) {
        this.fieldId = field.getId();
        this.title = field.getTitle();
        this.address = field.getAddress();
        this.fieldData = field.getFieldData();
        this.description = field.getDescription();
        this.images = field.getFieldImages().stream().map(BaseEntityImage::getStoreName).toList();
    }
}
