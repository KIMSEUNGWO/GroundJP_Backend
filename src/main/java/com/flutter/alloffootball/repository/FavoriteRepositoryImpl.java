package com.flutter.alloffootball.repository;

import com.flutter.alloffootball.common.domain.Favorite;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.jparepository.JpaFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final JpaFavoriteRepository jpaFavoriteRepository;

    @Override
    public void save(Favorite favorite) {
        jpaFavoriteRepository.save(favorite);
    }

    @Override
    public void deleteByUserIdAndFieldId(User user, Field field) {
        jpaFavoriteRepository.deleteByUserAndField(user, field);
    }
}
