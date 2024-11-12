package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.user.ResponseViewUser;
import com.flutter.alloffootball.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String user() {
        return "admin_user";
    }

    /**
     * 유저 정보 조회
     */
    @GetMapping("/{userId}")
    public String fieldViewPage(@PathVariable("userId") long userId, Model model) {
        ResponseViewUser viewUser = userService.findByIdViewUser(userId);
        model.addAttribute("user", viewUser);
        return "admin_user_view";
    }
}
