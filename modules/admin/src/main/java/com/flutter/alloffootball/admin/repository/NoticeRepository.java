package com.flutter.alloffootball.admin.repository;

import com.flutter.alloffootball.common.domain.admin.Notice;
import com.flutter.alloffootball.common.exception.NoticeError;
import com.flutter.alloffootball.common.exception.NoticeException;
import com.flutter.alloffootball.common.jparepository.JpaNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    private final JpaNoticeRepository jpaNoticeRepository;

    public Notice findByNoticeId(Long noticeId) {
        return jpaNoticeRepository.findById(noticeId)
            .orElseThrow(() -> new NoticeException(NoticeError.NOTICE_NOT_EXISTS));
    }

    public void save(Notice saveNotice) {
        jpaNoticeRepository.save(saveNotice);
    }

    public void deleteNotice(Long noticeId) {
        jpaNoticeRepository.deleteById(noticeId);
    }
}
