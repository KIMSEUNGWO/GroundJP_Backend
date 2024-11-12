package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.jparepository.JpaFavoriteRepository;
import com.flutter.alloffootball.user.dto.field.ResponseFavorite;
import com.flutter.alloffootball.user.dto.field.ResponseFieldData;
import com.flutter.alloffootball.user.dto.field.ResponseSearchField;
import com.flutter.alloffootball.user.querydsl.QueryDslFieldRepository;
import com.flutter.alloffootball.user.repository.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FieldServiceImpl implements FieldService {

    private final QueryDslFieldRepository queryDslFieldRepository;
    private final FieldRepository fieldRepository;
    private final JpaFavoriteRepository jpaFavoriteRepository;

    @Override
    public ResponseFieldData getFieldDetails(long fieldId, CustomUserDetails userDetails) {
        Field field = fieldRepository.findById(fieldId);
        return new ResponseFieldData(field);
    }

    @Override
    public List<ResponseFavorite> findAllByFavorite(Long userId) {
        return jpaFavoriteRepository.findAllByUser_Id(userId)
            .stream()
            .map(ResponseFavorite::new)
            .toList();
    }

    @Override
    public List<ResponseSearchField> search(String word) {
        return queryDslFieldRepository.search(word)
            .stream()
            .map(ResponseSearchField::new)
            .toList();
    }
}
