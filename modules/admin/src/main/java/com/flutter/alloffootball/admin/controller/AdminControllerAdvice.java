package com.flutter.alloffootball.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AdminControllerAdvice {

    // 메뉴이동간 path 확인을 위한 메소드
    // Thymeleaf에서 3.1부터 ServletRequest를 직접사용할 수 없기때문에 이와 같은 방법을 사용함
    // https://stackoverflow.com/questions/74594544/getrequesturi-is-null-with-netty-and-spring-boot-3
    @ModelAttribute("path")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }
}
