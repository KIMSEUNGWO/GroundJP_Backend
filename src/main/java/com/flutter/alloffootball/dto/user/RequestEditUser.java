package com.flutter.alloffootball.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestEditUser {

    private MultipartFile image;
    private String nickname;

    public boolean hasNickname() {
        return !(nickname == null || nickname.isEmpty());
    }
}
