package com.flutter.alloffootball.service;

import com.flutter.alloffootball.dto.user.RequestEditUser;
import com.flutter.alloffootball.dto.user.ResponseUserProfile;

public interface UserService {
    ResponseUserProfile getUserProfile(Long userId);

    boolean distinctNickname(String nickname);

    void editUser(Long userId, RequestEditUser editUser);
}
