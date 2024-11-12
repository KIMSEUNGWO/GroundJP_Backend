package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.user.ResponseViewUser;
import com.flutter.alloffootball.common.domain.user.User;
import com.flutter.alloffootball.common.exception.*;
import com.flutter.alloffootball.common.jparepository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JpaUserRepository jpaUserRepository;


    public ResponseViewUser findByIdViewUser(long userId) {
        User user = userFindById(userId);
        return new ResponseViewUser(user);
    }


    User userFindById(long userId) {
        return jpaUserRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserError.USER_NOT_EXISTS));
    }
}
