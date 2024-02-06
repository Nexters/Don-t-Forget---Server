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

    public LocalDate calculateCurSolarDate(final LocalDate date, final CalendarType type) {
        validateCalendarType(type);
        LocalDate dateTime = date;
        if (CalendarType.SOLAR == type) {
            dateTime = koreanLunar.convertLunarDateFromSolarDate(date);
        }
        return getCurSolarDate(dateTime);
    }

    private LocalDate getCurSolarDate(final LocalDate dateTime) {
        final LocalDate nowLunarDate = getCurLunarDate(dateTime);
        return koreanLunar.convertSolarDateFromLunarDate(nowLunarDate);
    }

    public LocalDate calculateCurLunarDate(final LocalDate date, final CalendarType type) {
        validateCalendarType(type);
        LocalDate dateTime = date;
        if (CalendarType.SOLAR == type) {
            dateTime = koreanLunar.convertLunarDateFromSolarDate(date);
        }
        return getCurLunarDate(dateTime);
    }

    private void validateCalendarType(CalendarType type) {
        if (!(CalendarType.LUNAR == type || CalendarType.SOLAR == type)) {
            throw new IllegalArgumentException("CalendarType이 잘못된 타입입니다.");
        }
    }

    private LocalDate getCurLunarDate(final LocalDate date) {
        return LocalDate.of(LocalDate.now().getYear(), date.getMonth(), date.getDayOfMonth());
    }
}
