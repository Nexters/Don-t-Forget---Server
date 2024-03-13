package com.dontforget.dontforget.domain.anniversary.query;

import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.domain.notice.enums.NoticeType;
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
    private CalendarType calendarType;
    private CardType cardType;
    private List<NoticeType> alarmSchedule;

    public CreateAnniversaryQuery(
        String deviceUuid, String title, LocalDate date,
        String content, CalendarType calendarType,
        CardType cardType,
        List<NoticeType> alarmSchedule
    ) {
        this.deviceUuid = deviceUuid;
        this.title = title;
        this.date = date;
        this.content = content;
        this.calendarType = calendarType;
        this.cardType = cardType;
        this.alarmSchedule = alarmSchedule;
    }
}
