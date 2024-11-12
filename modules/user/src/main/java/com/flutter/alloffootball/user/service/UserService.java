package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.user.dto.user.RequestEditUser;
import com.flutter.alloffootball.user.dto.user.ResponseUserProfile;

public interface UserService {
    ResponseUserProfile getUserProfile(Long userId);

    boolean distinctNickname(String nickname);

    void editUser(Long userId, RequestEditUser editUser);
}
