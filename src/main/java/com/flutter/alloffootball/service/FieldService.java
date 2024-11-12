package com.flutter.alloffootball.service;

import com.flutter.alloffootball.admin.dto.field.ResponseSearchField;
import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.dto.field.ResponseFavorite;
import com.flutter.alloffootball.dto.field.ResponseFieldData;

import java.util.List;

public interface FieldService {
    ResponseFieldData getFieldDetails(long fieldId, CustomUserDetails userDetails);

    List<ResponseFavorite> findAllByFavorite(Long userId);

    List<ResponseSearchField> search(String word);
}
