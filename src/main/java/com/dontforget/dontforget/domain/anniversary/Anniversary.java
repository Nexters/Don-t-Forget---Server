package com.dontforget.dontforget.domain.anniversary;

import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Anniversary {

    private Long id;

    private String title;

    private String content;

    private String deviceUuid;

    private LocalDate baseDate;

    private String baseType;

    private LocalDate lunarDate;

    private LocalDate solarDate;

    private List<Notice> notices = new ArrayList<>();

    private CardType cardType;

    public Anniversary(Long id,
        String title,
        String content,
        String deviceUuid,
        LocalDate baseDate,
        String baseType,
        LocalDate lunarDate,
        LocalDate solarDate,
        List<Notice> notices,
        CardType cardType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.deviceUuid = deviceUuid;
        this.baseDate = baseDate;
        this.baseType = baseType;
        this.lunarDate = lunarDate;
        this.solarDate = solarDate;
        this.notices = notices;
        this.cardType = cardType;
    }

    public Anniversary(
        String title,
        String content,
        String deviceUuid,
        LocalDate baseDate,
        String baseType,
        LocalDate lunarDate,
        LocalDate solarDate,
        List<Notice> notices,
        CardType cardType
    ) {
        this(null, title, content, deviceUuid,
            baseDate, baseType, lunarDate, solarDate, notices,
            cardType);
    }

    public static Anniversary create(
        String deviceUuid,
        String title,
        LocalDate date,
        String content,
        CalendarType type,
        List<NoticeType> alarmSchedule,
        CardType cardType,
        CalendarCalculator calendarCalculator
    ) {
        return new Anniversary(
            title,
            content,
            deviceUuid,
            date,
            type.name(),
            calendarCalculator.calculateCurLunarDate(date, type),
            calendarCalculator.calculateCurSolarDate(date, type),
            convertNotice(alarmSchedule),
            cardType
        );
    }

    public void update(
        String title, LocalDate date, CalendarType type,
        List<NoticeType> noticeTypes, String content,
        CalendarCalculator calculator
    ) {
        this.title = title;
        this.lunarDate = calculator.calculateCurLunarDate(date, type);
        this.solarDate = calculator.calculateCurSolarDate(date, type);
        this.notices = convertNotice(noticeTypes);
        this.content = content;
    }

    private static List<Notice> convertNotice(final List<NoticeType> alarmSchedule) {
        return alarmSchedule.stream()
            .map(it -> new Notice(null, it))
            .toList();
    }
}
