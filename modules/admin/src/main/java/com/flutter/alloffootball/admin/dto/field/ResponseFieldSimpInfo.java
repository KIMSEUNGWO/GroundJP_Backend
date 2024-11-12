package com.flutter.alloffootball.admin.dto.field;

import com.flutter.alloffootball.common.domain.field.Field;
import lombok.Getter;

@Getter
public class ResponseFieldSimpInfo {

    private final long fieldId;
    private final String title;

    public ResponseFieldSimpInfo(Field field) {
        this.fieldId = field.getId();
        this.title = field.getTitle();
    }
}
