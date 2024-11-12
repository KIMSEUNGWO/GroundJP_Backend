package com.flutter.alloffootball.common.domain.field;

import com.flutter.alloffootball.common.enums.field.Parking;
import com.flutter.alloffootball.common.enums.field.Shower;
import com.flutter.alloffootball.common.enums.field.Toilet;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldData {

    @Enumerated(EnumType.STRING)
    private Parking parking;

    @Enumerated(EnumType.STRING)
    private Shower shower;

    @Enumerated(EnumType.STRING)
    private Toilet toilet;

    private int size; // 제곱미터

}
