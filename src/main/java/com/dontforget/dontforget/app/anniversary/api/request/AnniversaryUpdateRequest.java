package com.dontforget.dontforget.app.anniversary.api.request;

import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.domain.anniversary.query.UpdateAnniversaryQuery;
import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDate;
import java.util.List;

public record AnniversaryUpdateRequest(String title, LocalDate date, CalendarType type,
    List<NoticeType> alarmSchedule, String content
) {

    public UpdateAnniversaryQuery toQuery(Long anniversaryId) {
        return new UpdateAnniversaryQuery(
            anniversaryId, title, date, type,
            alarmSchedule, content
        );
    }
}
