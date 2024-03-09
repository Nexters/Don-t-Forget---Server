package com.dontforget.dontforget.domain.notice;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Notice {

    private Long id;

    private Long anniversaryId;

    private NoticeType noticeType = NoticeType.D_DAY;

    private NoticeStatus noticeStatus = NoticeStatus.WAITING_SEND;

    public Notice(Long id, Long anniversaryId, NoticeType noticeType, NoticeStatus noticeStatus) {
        this.id = id;
        this.anniversaryId = anniversaryId;
        this.noticeType = noticeType;
        this.noticeStatus = noticeStatus;
    }

    public Notice(final Long anniversaryId, final NoticeType noticeType,
        final NoticeStatus noticeStatus) {
        this(null, anniversaryId, noticeType, noticeStatus);
    }

    public Notice(final Long anniversaryId, final NoticeType noticeType) {
        this(null, anniversaryId, noticeType, NoticeStatus.WAITING_SEND);
    }

    public boolean isSendNotice(final LocalDate searchDay, final LocalDate noticeDate) {
        return calculateNoticeTypeDate(searchDay).isEqual(noticeDate)
            && noticeStatus == NoticeStatus.WAITING_SEND;
    }

    private LocalDate calculateNoticeTypeDate(final LocalDate searchDay) {
        return searchDay.minusDays(noticeType.getDay());
    }
}
