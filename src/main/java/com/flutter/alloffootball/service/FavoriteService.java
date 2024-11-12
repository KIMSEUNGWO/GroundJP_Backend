package com.flutter.alloffootball.service;

import com.flutter.alloffootball.dto.user.RequestFavoriteToggle;

public interface FavoriteService {
    void favoriteToggle(Long userId, RequestFavoriteToggle favoriteToggle);
}
