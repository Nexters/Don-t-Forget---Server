package com.dontforget.dontforget.domain.notice;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Notice {

  private Long id;

  private Long anniversaryId;

  private NoticeType noticeType = NoticeType.D_DAY;

  public Notice(Long id, Long anniversaryId, NoticeType noticeType) {
    this.id = id;
    this.anniversaryId = anniversaryId;
    this.noticeType = noticeType;
  }
}
