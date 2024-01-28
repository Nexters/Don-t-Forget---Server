package com.dontforget.dontforget.infra.notice;

import com.dontforget.dontforget.domain.notice.NoticeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "anniversary_id", nullable = false)
  private Long anniversaryId;

  @Column(name = "notice_type", nullable = false)
  @Enumerated
  private NoticeType noticeType;

  public NoticeEntity(Long anniversaryId, NoticeType noticeType) {
    this(null, anniversaryId, noticeType);
  }
}
