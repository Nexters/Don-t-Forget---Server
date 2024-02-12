package com.dontforget.dontforget.app.anniversary.api.request;

import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.domain.anniversary.query.UpdateAnniversaryQuery;
import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnniversaryUpdateRequest {

    private String title;

    private LocalDate date;

    private CalendarType calendarType;

    private String content;

    private List<NoticeType> alarmSchedule;

    public AnniversaryUpdateRequest(
        String title,
        LocalDate date,
        CalendarType calendarType,
        List<NoticeType> alarmSchedule,
        String content
        ) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.calendarType = calendarType;
        this.alarmSchedule = alarmSchedule;
    }
    public UpdateAnniversaryQuery toQuery(Long anniversaryId) {
        return new UpdateAnniversaryQuery(
            anniversaryId, title, date, calendarType,
            alarmSchedule, content
        );
    }
}
