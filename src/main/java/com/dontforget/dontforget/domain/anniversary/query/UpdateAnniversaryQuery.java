package com.dontforget.dontforget.domain.anniversary.query;

import com.dontforget.dontforget.common.CalenderType;
import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDate;
import java.util.List;

public record UpdateAnniversaryQuery(
    Long anniversaryId,String title, LocalDate date, CalenderType type,
    List<NoticeType> alarmSchedule, String content) {
}
