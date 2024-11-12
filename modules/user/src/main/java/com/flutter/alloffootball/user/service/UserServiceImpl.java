package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.component.DataValidator;
import com.flutter.alloffootball.common.component.file.FileService;
import com.flutter.alloffootball.common.component.file.FileType;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.dto.InvalidData;
import com.flutter.alloffootball.common.exception.InvalidDataException;
import com.flutter.alloffootball.user.dto.user.RequestEditUser;
import com.flutter.alloffootball.user.dto.user.ResponseUserProfile;
import com.flutter.alloffootball.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final FileService fileService;
    private final UserRepository userRepository;

    private final DataValidator dataValidator;

    @Override
    public ResponseUserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId);
        return new ResponseUserProfile(user);
    }

    @Override
    public boolean distinctNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public void editUser(Long userId, RequestEditUser editUser) {

        dataValidator.validNickname(editUser.getNickname());

        Consumer<User> consumers = User::getId;

        if (editUser.hasNickname()) {
            if (distinctNickname(editUser.getNickname())) {
                throw new InvalidDataException(new InvalidData("nickname", "이미 사용중인 닉네임입니다."));
            }
            consumers = consumers.andThen(user -> user.setNickname(editUser.getNickname()));
        }


        User user = userRepository.findById(userId);
        consumers.accept(user);

        fileService.editImage(editUser.getImage(), user.getProfile(), FileType.PROFILE);
    }
}
