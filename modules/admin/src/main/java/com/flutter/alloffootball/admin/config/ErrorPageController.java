package com.flutter.alloffootball.admin.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/400")
    public String errorPage404() {
        return "error-page/400";
    }

    @RequestMapping("/error-page/403")
    public String errorPage403() {
        return "error-page/403";
    }
}
