package com.dontforget.dontforget.app.anniversary.api.request;

import com.dontforget.dontforget.common.CalenderType;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnniversaryCreateRequest {

    private String title;

    private LocalDate date;

    private String content;

    private CalenderType type;

    private List<NoticeType> alarmSchedule;

    public AnniversaryCreateRequest(
        String title, LocalDate date,
        String content, CalenderType type,
        List<NoticeType> alarmSchedule
    ) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.type = type;
        this.alarmSchedule = alarmSchedule;
    }

    public CreateAnniversaryQuery toQuery(final String deviceUuid) {
        return new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, type, alarmSchedule
        );
    }
}
