package com.dontforget.dontforget.domain.anniversary.query;

import com.dontforget.dontforget.common.CalenderType;
import com.dontforget.dontforget.domain.notice.NoticeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateAnniversaryQuery {

    private String deviceUuid;
    private String title;
    private LocalDate date;
    private String content;
    private CalenderType type;
    private List<NoticeType> alarmSchedule;

    public CreateAnniversaryQuery(
        String deviceUuid, String title, LocalDate date,
        String content, CalenderType type, List<NoticeType> alarmSchedule
    ) {
        this.deviceUuid = deviceUuid;
        this.title = title;
        this.date = date;
        this.content = content;
        this.type = type;
        this.alarmSchedule = alarmSchedule;
    }
}
