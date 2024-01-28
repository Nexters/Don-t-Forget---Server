package com.dontforget.dontforget.app.anniversary.api.request;

import com.dontforget.dontforget.app.anniversary.application.query.CreateAnnivarsaryQuery;
import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnniversaryCreateRequest {

  private String title;

  private LocalDateTime date;

  private String content;

  private String type;

  private List<NoticeType> alarmSchedule;

  public AnniversaryCreateRequest(String title, LocalDateTime date, String content, String type,
      List<NoticeType> alarmSchedule) {
    this.title = title;
    this.date = date;
    this.content = content;
    this.type = type;
    this.alarmSchedule = alarmSchedule;
  }

  public CreateAnnivarsaryQuery toQuery(String deviceUuid) {
    return new CreateAnnivarsaryQuery(
        deviceUuid = deviceUuid,
        title = title,
        date = date,
        content = content,
        type = type,
        alarmSchedule = alarmSchedule);
  }
}
