package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.match.RequestSearchMatch;
import com.flutter.alloffootball.admin.service.AdminPageService;
import com.flutter.alloffootball.admin.dto.PageMatch;
import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/match")
public class AdminRestMatchController {

    private final AdminPageService adminPageService;

    @GetMapping("/get")
    public ResponseEntity<Response> matchList(@ModelAttribute RequestSearchMatch data) {
        Pageable pageable = PageRequest.of(data.getPage() - 1, 10);
        return Response.ok(new PageMatch<>(adminPageService.findAllBySearchMatch(data, pageable), data));
    }
}
