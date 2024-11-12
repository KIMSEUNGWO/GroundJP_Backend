package com.flutter.alloffootball.admin.controller;

import com.flutter.alloffootball.admin.dto.notice.RequestSearchNotice;
import com.flutter.alloffootball.admin.dto.notice.ResponseNoticeListView;
import com.flutter.alloffootball.admin.service.AdminPageService;
import com.flutter.alloffootball.common.dto.PageDto;
import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminRestNoticeController {

    private final AdminPageService adminPageService;

    @GetMapping("/get")
    public ResponseEntity<Response> noticeList(@ModelAttribute RequestSearchNotice data) {
        Pageable pageable = PageRequest.of(data.getPage() - 1, 10);
        Page<ResponseNoticeListView> result = adminPageService.findAllBySearchNotice(pageable);
        return Response.ok(new PageDto<>(result));
    }
}
