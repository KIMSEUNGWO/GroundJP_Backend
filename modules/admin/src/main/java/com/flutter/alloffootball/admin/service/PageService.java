package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.field.RequestSearchField;
import com.flutter.alloffootball.admin.dto.field.ResponseSearchField;
import com.flutter.alloffootball.admin.dto.match.RequestSearchMatch;
import com.flutter.alloffootball.admin.dto.match.ResponseSearchMatch;
import com.flutter.alloffootball.admin.dto.notice.ResponseNoticeListView;
import com.flutter.alloffootball.admin.dto.user.RequestSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseSearchUser;
import com.flutter.alloffootball.admin.dto.user.ResponseUserOrder;
import com.flutter.alloffootball.admin.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PageService {

    private final PageRepository pageRepository;

    public Page<ResponseSearchField> findAllBySearchField(RequestSearchField data, Pageable pageable) {
        return pageRepository.findAllBySearchField(data, pageable);
    }

    public Page<ResponseSearchMatch> findAllBySearchMatch(RequestSearchMatch data, Pageable pageable) {
        return pageRepository.findAllBySearchMatch(data, pageable);
    }

    public Page<ResponseSearchUser> findAllBySearchUser(RequestSearchUser data, Pageable pageable) {
        return pageRepository.findAllBySearchUser(data, pageable);
    }

    public Page<ResponseUserOrder> findAllByUserOrder(Long userId, Pageable pageable) {
        return pageRepository.findAllByUserOrder(userId, pageable);
    }

    public Page<ResponseNoticeListView> findAllBySearchNotice(Pageable pageable) {
        return pageRepository.findAllBySearchNotice(pageable);
    }
}
