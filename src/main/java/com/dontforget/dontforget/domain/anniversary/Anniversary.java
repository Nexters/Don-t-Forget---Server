package com.dontforget.dontforget.domain.anniversary;

import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.enums.NoticeType;
import java.time.LocalDate;
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

    public static Anniversary createNextAnniversary(
        Anniversary anniversary,
        CalendarCalculator calendarCalculator
    ) {
        LocalDate nextDate = LocalDate.of(
            anniversary.getBaseDate().getYear() + 1,
            anniversary.getBaseDate().getMonth(),
            anniversary.getBaseDate().getDayOfMonth()
        );
        CalendarType type = CalendarType.valueOf(anniversary.getBaseType());

        return new Anniversary(
            anniversary.getTitle(),
            anniversary.getContent(),
            anniversary.getDeviceUuid(),
            nextDate,
            anniversary.getBaseType(),
            calendarCalculator.calculateCurLunarDate(nextDate, type),
            calendarCalculator.calculateCurSolarDate(nextDate, type),
            anniversary.getNotices(),
            anniversary.getCardType()
        );
    }

    private static List<Notice> convertNotice(final List<NoticeType> alarmSchedule) {
        return alarmSchedule.stream()
            .map(it -> new Notice(null, it))
            .toList();
    }

    public void update(String title, LocalDate date, CalendarType type,
        List<NoticeType> noticeTypes, String content, CalendarCalculator calendarCalculator
    ) {
        this.title = title;
        this.baseDate = date;
        this.baseType = type.name();
        this.lunarDate = calendarCalculator.calculateCurLunarDate(date, type);
        this.solarDate = calendarCalculator.calculateCurSolarDate(date, type);
        this.notices = convertNotice(noticeTypes);
        this.content = content;
    }
}
