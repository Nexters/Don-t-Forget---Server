package com.dontforget.dontforget.domain.anniversary;

import com.dontforget.dontforget.domain.notice.Notice;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Anniversary {

  private Long id;

  private String title;

  private String content;

  private String deviceUuid;

  private LocalDateTime lunarDate;

  private LocalDateTime solarDate;

  private List<Notice> notices = new ArrayList<>();

  public Anniversary(Long id, String title, String content, String deviceUuid,
      LocalDateTime lunarDate, LocalDateTime solarDate, List<Notice> notices) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.deviceUuid = deviceUuid;
    this.lunarDate = lunarDate;
    this.solarDate = solarDate;
    this.notices = notices;
  }

  public Anniversary(String title, String content, String deviceUuid, LocalDateTime lunarDate,
      LocalDateTime solarDate, List<Notice> notices) {
    this(null, title, content, deviceUuid, lunarDate, solarDate, notices);
  }
}
