package com.flutter.alloffootball.user.repository;

import com.flutter.alloffootball.common.domain.Favorite;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.user.User;

public interface FavoriteRepository {

    void save(Favorite favorite);

    void deleteByUserIdAndFieldId(User user, Field field);
}
