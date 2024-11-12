package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.user.dto.user.RequestFavoriteToggle;

public interface FavoriteService {
    void favoriteToggle(Long userId, RequestFavoriteToggle favoriteToggle);
}
