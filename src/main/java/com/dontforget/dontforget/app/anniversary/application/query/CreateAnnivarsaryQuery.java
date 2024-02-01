package com.dontforget.dontforget.app.anniversary.application.query;

import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAnnivarsaryQuery {

  private String deviceUuid;
  private String title;
  private LocalDateTime date;
  private String content;
  private String type;
  private List<NoticeType> alarmSchedule;

  public CreateAnnivarsaryQuery(String deviceUuid, String title, LocalDateTime date, String content,
      String type,
      List<NoticeType> alarmSchedule) {
    this.deviceUuid = deviceUuid;
    this.title = title;
    this.date = date;
    this.content = content;
    this.type = type;
    this.alarmSchedule = alarmSchedule;
  }
}
