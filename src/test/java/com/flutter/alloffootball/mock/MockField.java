package com.flutter.alloffootball.mock;

import com.flutter.alloffootball.common.domain.field.Address;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldData;
import com.flutter.alloffootball.common.enums.field.Parking;
import com.flutter.alloffootball.common.enums.field.Shower;
import com.flutter.alloffootball.common.enums.field.Toilet;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.common.jparepository.JpaFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockField {

    private final JpaFieldRepository fieldRepository;

    @Autowired
    public MockField(JpaFieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public Field mockField() {
        Field saveField = Field.builder()
            .title("구장 이름")
            .description("어쩌구 저쩌구 설명")
            .address(Address.builder()
                .address("인천 어디어디")
                .region(Region.AOMORI)
                .build())
            .fieldData(FieldData.builder()
                .parking(Parking.FREE)
                .shower(Shower.Y)
                .toilet(Toilet.Y)
                .size(123)
                .build())
            .build();
        return fieldRepository.save(saveField);
    }
}
