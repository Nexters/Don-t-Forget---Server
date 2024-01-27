package com.dontforget.dontforget.infra.jpa.notice;

import com.dontforget.dontforget.domain.notice.NoticeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    public NoticeEntity(Long id, Long anniversaryId, NoticeType noticeType) {
        this.id = id;
        this.anniversaryId = anniversaryId;
        this.noticeType = noticeType;
    }

    public NoticeEntity(Long anniversaryId, NoticeType noticeType) {
        this(null, anniversaryId, noticeType);
    }
}
