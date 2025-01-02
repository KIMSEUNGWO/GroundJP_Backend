package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.user.dto.field.ResponseFavorite;
import com.flutter.alloffootball.user.dto.field.ResponseFieldData;
import com.flutter.alloffootball.user.dto.field.ResponseSearchField;

import java.util.List;

public interface FieldService {
    ResponseFieldData getFieldDetails(long fieldId, Long userId);

    List<ResponseFavorite> findAllByFavorite(Long userId);

    List<ResponseSearchField> search(String word);
}
