package com.flutter.alloffootball.user.service;

import com.flutter.alloffootball.common.querydsl.QueryNoticeSupport;
import com.flutter.alloffootball.user.dto.notice.RequestNotice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final QueryNoticeSupport noticeSupport;

    public Page<RequestNotice> findAllBySearchNotice(Pageable pageable) {
        return noticeSupport.findAllBySearchNotice(pageable).map(RequestNotice::new);
    }
}
