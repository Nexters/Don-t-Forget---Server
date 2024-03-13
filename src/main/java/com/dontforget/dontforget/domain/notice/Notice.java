package com.dontforget.dontforget.domain.notice;

import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.domain.notice.enums.NoticeType;
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

    public boolean isSendable(
        final LocalDate now,
        final LocalDate dDay
    ) {
        return now.plusDays(noticeType.getDay()).isEqual(dDay)
            && noticeStatus == NoticeStatus.WAITING_SEND;
    }

}
