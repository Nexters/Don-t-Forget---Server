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

    private CardType cardType = CardType.ARM;

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
        if (type == null) {
            throw new IllegalArgumentException("CalendarType이 잘못된 타입입니다.");
        }
        int year = LocalDate.now().getYear();
        return new Anniversary(
            title,
            content,
            deviceUuid,
            date,
            type.name(),
            calendarCalculator.calculateLunarDate(date, type, year),
            calendarCalculator.calculateSolarDate(date, type, year),
            convertNotice(alarmSchedule),
            cardType
        );
    }

    public static Anniversary createNextAnniversary(
        Anniversary anniversary,
        CalendarCalculator calendarCalculator
    ) {
        LocalDate nextLunarDate = LocalDate.of(
            anniversary.getLunarDate().getYear() + 1,
            anniversary.getLunarDate().getMonth(),
            anniversary.getLunarDate().getDayOfMonth()
        );
        CalendarType type = CalendarType.valueOf(CalendarType.LUNAR.name());
        int year = LocalDate.now().getYear() + 1;
        LocalDate nextSolarDate = calendarCalculator.calculateSolarDate(nextLunarDate, type, year);
        LocalDate nextDate = (anniversary.getBaseType().equals(CalendarType.LUNAR.name()))
            ? nextLunarDate : nextSolarDate;

        return new Anniversary(
            anniversary.getId(),
            anniversary.getTitle(),
            anniversary.getContent(),
            anniversary.getDeviceUuid(),
            nextDate,
            anniversary.getBaseType(),
            nextLunarDate,
            nextSolarDate,
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
        int year = LocalDate.now().getYear();
        this.title = title;
        this.baseDate = date;
        this.baseType = type.name();
        this.lunarDate = calendarCalculator.calculateLunarDate(date, type, year);
        this.solarDate = calendarCalculator.calculateSolarDate(date, type, year);
        this.notices = convertNotice(noticeTypes);
        this.content = content;
    }
}
