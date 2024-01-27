package com.dontforget.dontforget.infra.notice;

import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anniversary_id", nullable = false)
    private Long anniversaryId;

    @Column(name = "notice_type", nullable = false)
    @Enumerated
    private NoticeType noticeType;

    public NoticeEntity(Long id, Long anniversaryId, NoticeType noticeType) {
        this.id = id;
        this.anniversaryId = anniversaryId;
        this.noticeType = noticeType;
    }

    public NoticeEntity(Long anniversaryId, NoticeType noticeType) {
        this(null, anniversaryId, noticeType);
    }

    public static NoticeEntity of(final Notice notice, final Long anniversaryId) {
        return new NoticeEntity(
            notice.getId(),
            anniversaryId,
            notice.getNoticeType()
        );
    }

    public Notice toDomain() {
        return new Notice(
            this.id,
            this.anniversaryId,
            this.noticeType
        );
    }

    public static NoticeEntity from(final Notice notice) {
        return new NoticeEntity(
            notice.getId(),
            notice.getAnniversaryId(),
            notice.getNoticeType()
        );
    }
}
