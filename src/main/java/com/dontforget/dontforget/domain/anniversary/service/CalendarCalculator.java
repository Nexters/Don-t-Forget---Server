package com.dontforget.dontforget.domain.anniversary.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.common.CalenderType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@DomainService
@RequiredArgsConstructor
public class CalendarCalculator {

    private final KoreanLunarCalendarCalculator koreanLunar;

    public LocalDate calculateSolarDate(final LocalDate dateTime, final CalenderType type) {
        if (CalenderType.LUNAR == type) {
            return getCurSolarDate(dateTime);
        }

        if (CalenderType.SOLAR == type) {
            koreanLunar.setSolarDate(dateTime);
            return getCurSolarDate(koreanLunar.getLunarDate());
        }

        throw new IllegalArgumentException("잘못된 타입입니다.");
    }

    private LocalDate getCurSolarDate(final LocalDate dateTime) {
        final LocalDate nowLunarDate = getCurLunarDate(dateTime);
        koreanLunar.setLunarDate(nowLunarDate);
        return koreanLunar.getSolarDate();
    }

    public LocalDate calculateLunarDate(final LocalDate date, final CalenderType type) {
        if (CalenderType.LUNAR == type) {
            return getCurLunarDate(date);
        }

        if (CalenderType.SOLAR == type) {
            koreanLunar.setSolarDate(date);
            return getCurLunarDate(koreanLunar.getLunarDate());
        }
        throw new IllegalArgumentException("잘못된 타입입니다.");
    }

    private LocalDate getCurLunarDate(final LocalDate date) {
        return LocalDate.of(LocalDate.now().getYear(), date.getMonth(), date.getDayOfMonth());
    }
}
