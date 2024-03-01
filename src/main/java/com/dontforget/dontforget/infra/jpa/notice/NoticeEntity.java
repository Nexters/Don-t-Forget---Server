package com.dontforget.dontforget.infra.jpa.notice;

import com.dontforget.dontforget.domain.notice.NoticeStatus;
import com.dontforget.dontforget.domain.notice.NoticeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notice")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anniversary_id", nullable = false)
    private Long anniversaryId;

    @Column(name = "notice_type", nullable = false)
    @Enumerated
    private NoticeType noticeType;

    @Column(name = "notice_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private NoticeStatus noticeStatus;

    public NoticeEntity(Long id, Long anniversaryId, NoticeType noticeType,
        NoticeStatus noticeStatus) {
        this.id = id;
        this.anniversaryId = anniversaryId;
        this.noticeType = noticeType;
        this.noticeStatus = noticeStatus;
    }

    public NoticeEntity(Long anniversaryId, NoticeType noticeType, NoticeStatus noticeStatus) {
        this(null, anniversaryId, noticeType, noticeStatus);
    }

    public NoticeEntity(Long anniversaryId, NoticeType noticeType) {
        this(null, anniversaryId, noticeType, NoticeStatus.WAITING_SEND);
    }
}
