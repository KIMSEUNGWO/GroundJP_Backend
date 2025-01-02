package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.user.api.SocialProfile;
import com.flutter.alloffootball.common.domain.user.Profile;
import com.flutter.alloffootball.common.domain.user.Social;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.domain.user.UserInfo;
import com.flutter.alloffootball.user.dto.login.RegisterRequest;
import com.flutter.alloffootball.common.enums.Role;
import com.flutter.alloffootball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    @Override
    public Long register(RegisterRequest register, SocialProfile profile) {
        Social social = Social.builder()
            .socialId(profile.getUserId())
            .provider(register.getProvider())
            .build();

        UserInfo userInfo = UserInfo.builder()
            .sex(register.getSex())
            .birth(register.getBirth())
            .build();

        User user = User.builder()
            .nickname(String.format("%s#%s", profile.getDisplayName(), register.getSocialId().substring(0, 5)))
            .profile(new Profile())
            .userInfo(userInfo)
            .social(social)
            .role(Role.USER)
            .build();

        return userRepository.save(user).getId();
    }
}
