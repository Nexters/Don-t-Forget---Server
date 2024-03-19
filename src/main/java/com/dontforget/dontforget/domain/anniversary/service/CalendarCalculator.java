package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@DomainService
@RequiredArgsConstructor
public class CalendarCalculator {

    private final KoreanLunarCalendarCalculator koreanLunar;

    public LocalDate calculateSolarDate(final LocalDate date, final CalendarType type, final int year) {
        validateCalendarType(type);
        LocalDate dateTime = date;
        if (CalendarType.SOLAR == type) {
            dateTime = koreanLunar.convertLunarDateFromSolarDate(date);
        }
        return getSolarDate(dateTime, year);
    }

    private LocalDate getSolarDate(final LocalDate dateTime, final int year) {
        final LocalDate nowLunarDate = getLunarDate(dateTime, year);
        return koreanLunar.convertSolarDateFromLunarDate(nowLunarDate);
    }

    public LocalDate calculateLunarDate(final LocalDate date, final CalendarType type, final int year) {
        validateCalendarType(type);
        LocalDate dateTime = date;
        if (CalendarType.SOLAR == type) {
            dateTime = koreanLunar.convertLunarDateFromSolarDate(date);
        }
        return getLunarDate(dateTime, LocalDate.now().getYear());
    }

    private void validateCalendarType(CalendarType type) {
        if (!(CalendarType.LUNAR == type || CalendarType.SOLAR == type)) {
            throw new IllegalArgumentException("CalendarType이 잘못된 타입입니다.");
        }
    }

    private LocalDate getLunarDate(final LocalDate date, final int year) {
        return LocalDate.of(year, date.getMonth(), date.getDayOfMonth());
    }
}
