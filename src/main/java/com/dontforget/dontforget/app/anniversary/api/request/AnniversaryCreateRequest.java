package com.dontforget.dontforget.app.anniversary.api.request;

import com.dontforget.dontforget.app.anniversary.application.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.notice.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnniversaryCreateRequest {

    private String title;

    private LocalDate date;

    private String content;

    private String type;

    private List<NoticeType> alarmSchedule;

    public CreateAnniversaryQuery toQuery(final String deviceUuid) {
        return new CreateAnniversaryQuery(
                deviceUuid, title, date,
                content, type, alarmSchedule
        );
    }
}
