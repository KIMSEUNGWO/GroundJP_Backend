package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.field.RequestSaveFieldForm;
import com.flutter.alloffootball.admin.dto.field.ResponseEditField;
import com.flutter.alloffootball.admin.dto.field.ResponseFieldSimpInfo;
import com.flutter.alloffootball.admin.dto.field.ResponseViewField;
import com.flutter.alloffootball.admin.repository.FieldRepository;
import com.flutter.alloffootball.common.component.file.FileService;
import com.flutter.alloffootball.common.domain.field.Address;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldData;
import com.flutter.alloffootball.common.jparepository.JpaFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class FieldService {

    private final FieldRepository fieldRepository;
    private final JpaFieldRepository jpaFieldRepository;
    private final FileService fileService;

    public ResponseViewField findByIdViewField(long fieldId) {
        Field field = fieldFindById(fieldId);
        return new ResponseViewField(field);
    }

    public void saveField(RequestSaveFieldForm form) {
        System.out.println("form = " + form);
        Field saveField = Field.builder()
            .title(form.getTitle())
            .description(form.getDescription())
            .address(Address.builder()
                .address(form.getAddress())
                .region(form.getRegion())
                .link(form.getLink())
                .build())
            .fieldData(FieldData.builder()
                .size(form.getSize())
                .parking(form.getParking())
                .shower(form.getShower())
                .toilet(form.getToilet())
                .build())
            .build();

        jpaFieldRepository.save(saveField);

        fileService.saveFieldImages(form.getImages(), saveField);

    }

    public ResponseEditField getEditFieldForm(Long fieldId) {
        Field field = fieldFindById(fieldId);
        return new ResponseEditField(field);
    }

    public void patchEditField(Long fieldId, ResponseEditField editField) {
        Field field = fieldFindById(fieldId);

        String[] deleteStoreImages = editField.getDeleteImages().split(",");
        fileService.deleteImages(deleteStoreImages);
        fileService.saveFieldImages(editField.getImages(), field);

        field.setTitle(editField.getTitle());
        field.setDescription(editField.getDescription());

        Address address = field.getAddress();
        address.setLink(editField.getLink());
        address.setRegion(editField.getRegion());
        address.setAddress(editField.getAddress());
        System.out.println("address = " + address);

        FieldData fieldData = field.getFieldData();
        fieldData.setParking(editField.getParking());
        fieldData.setShower(editField.getShower());
        fieldData.setToilet(editField.getToilet());
        fieldData.setSize(editField.getSize());

    }

    public ResponseFieldSimpInfo findByIdFieldSimpInfo(long fieldId) {
        Field field = fieldFindById(fieldId);
        return new ResponseFieldSimpInfo(field);
    }

    private Field fieldFindById(long fieldId) {
        return Objects.requireNonNull(fieldRepository.fieldFindById(fieldId));
    }

}
