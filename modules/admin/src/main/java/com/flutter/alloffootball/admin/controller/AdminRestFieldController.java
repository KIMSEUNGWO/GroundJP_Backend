package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.field.RequestSearchField;
import com.flutter.alloffootball.admin.service.AdminPageService;
import com.flutter.alloffootball.admin.service.AdminService;
import com.flutter.alloffootball.admin.dto.PageField;
import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/field")
public class AdminRestFieldController {

    private final AdminPageService adminPageService;

    @GetMapping("/get")
    public ResponseEntity<Response> fieldList(@ModelAttribute RequestSearchField data) {
        System.out.println("data = " + data);
        Pageable pageable = PageRequest.of(data.getPage() - 1, 10);
        return Response.ok(new PageField<>(adminPageService.findAllBySearchField(data, pageable), data));
    }
}
