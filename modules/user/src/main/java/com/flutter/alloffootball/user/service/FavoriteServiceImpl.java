package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.domain.Favorite;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.user.dto.user.RequestFavoriteToggle;
import com.flutter.alloffootball.user.repository.FavoriteRepository;
import com.flutter.alloffootball.user.repository.FieldRepository;
import com.flutter.alloffootball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public void favoriteToggle(Long userId, RequestFavoriteToggle request) {
        User user = userRepository.findById(userId);
        Field field = fieldRepository.findById(request.getFieldId());

        if (request.isToggle()) { // 사용자가 즐겨찾기를 추가하면
            favoriteRepository.save(Favorite.builder()
                .user(user)
                .field(field)
                .build());
        } else { // 사용자가 즐겨찾기를 취소하면
            favoriteRepository.deleteByUserIdAndFieldId(user, field);
        }
    }
}
