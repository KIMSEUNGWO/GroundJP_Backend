package com.flutter.alloffootball.admin.service;

import com.flutter.alloffootball.admin.dto.notice.RequestEditNoticeForm;
import com.flutter.alloffootball.admin.dto.notice.RequestSaveNoticeForm;
import com.flutter.alloffootball.admin.dto.notice.ResponseNoticeView;
import com.flutter.alloffootball.admin.repository.NoticeRepository;
import com.flutter.alloffootball.common.domain.admin.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public ResponseNoticeView findByNoticeId(Long noticeId) {
        Notice notice = getByNoticeId(noticeId);
        return new ResponseNoticeView(notice);
    }

    public void saveNotice(RequestSaveNoticeForm form) {
        Notice saveNotice = Notice.builder()
            .title(form.getTitle())
            .content(form.getContent())
            .build();
        noticeRepository.save(saveNotice);
    }

    public RequestEditNoticeForm getEditForm(Long noticeId) {
        Notice notice = getByNoticeId(noticeId);
        return new RequestEditNoticeForm(notice);
    }

    private Notice getByNoticeId(Long noticeId) {
        return noticeRepository.findByNoticeId(noticeId);
    }

    public void editNotice(Long noticeId, RequestEditNoticeForm form) {
        Notice notice = getByNoticeId(noticeId);
        notice.update(form.getTitle(), form.getContent());
    }

    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteNotice(noticeId);
    }
}
