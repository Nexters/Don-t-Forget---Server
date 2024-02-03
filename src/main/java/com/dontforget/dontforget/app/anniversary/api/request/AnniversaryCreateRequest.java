package com.dontforget.dontforget.app.anniversary.api.request;

import com.dontforget.dontforget.common.CalenderType;
import com.dontforget.dontforget.common.CardType;
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

    private CalenderType calenderType;

    private CardType cardType;
    private List<NoticeType> alarmSchedule;

    public AnniversaryCreateRequest(
        String title, LocalDate date,
        String content, CalenderType calenderType,
        CardType cardType,
        List<NoticeType> alarmSchedule
    ) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.calenderType = calenderType;
        this.cardType = cardType;
        this.alarmSchedule = alarmSchedule;
    }

    public CreateAnniversaryQuery toQuery(final String deviceUuid) {
        return new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, calenderType, cardType, alarmSchedule
        );
    }
}
