package com.flutter.alloffootball.admin.dto.field;

import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseEditField extends FieldForm{

    private List<MultipartFile> images; // 새로 추가될 사진목록
    private List<FieldImage> savedImages; // 이미 DB에 저장되어있는 사진목록
    private String deleteImages;

    public ResponseEditField(Field field) {
        super(field);
        // 강제 초기화
        int size = field.getFieldImages().size();
        savedImages = field.getFieldImages();
    }
}
