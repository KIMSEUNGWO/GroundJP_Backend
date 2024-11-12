package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.field.RequestSearchField;
import com.flutter.alloffootball.admin.service.PageService;
import com.flutter.alloffootball.admin.dto.PageField;
import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/field")
public class FieldRestController {

    private final PageService pageService;

    @GetMapping("/get")
    public ResponseEntity<Response> fieldList(@ModelAttribute RequestSearchField data, Locale locale) {
        System.out.println("data = " + data);
        System.out.println("locale = " + locale);
        Pageable pageable = PageRequest.of(data.getPage() - 1, 10);
        return Response.ok(new PageField<>(pageService.findAllBySearchField(data, pageable), data));
    }
}
