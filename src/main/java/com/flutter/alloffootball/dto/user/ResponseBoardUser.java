package com.flutter.alloffootball.dto.user;

import com.flutter.alloffootball.common.domain.user.User;
import lombok.Getter;

@Getter
public class ResponseBoardUser {

    private final long userId;
    private final String image;
    private final String nickname;

    public ResponseBoardUser(User user) {
        userId = user.getId();
        image = user.getProfile().getThumbnailName();
        nickname = user.getNickname();
    }
}
