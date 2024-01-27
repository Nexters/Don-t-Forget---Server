package com.dontforget.dontforget.app.anniversary.application.service;

import com.dontforget.dontforget.common.DomainService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@DomainService
@RequiredArgsConstructor
public class CalendarCalculator {
    private static final String LUNAR = "lunar";
    private static final String SOLAR = "solar";

    private final KoreanLunarCalendarCalculator koreanLunar;

    public LocalDate calculateSolarDate(final LocalDate dateTime, final String type) {
        if (LUNAR.equals(type)) {
            return getCurSolarDate(dateTime);
        }

        if (SOLAR.equals(type)) {
            koreanLunar.setSolarDate(dateTime);
            final LocalDate lunarConvertDate = koreanLunar.getLunarDate();
            return getCurSolarDate(lunarConvertDate);
        }

        throw new IllegalArgumentException("잘못된 타입입니다.");
    }

    private LocalDate getCurSolarDate(final LocalDate dateTime) {
        LocalDate nowLunarDate = getCurLunarDate(dateTime);
        koreanLunar.setLunarDate(nowLunarDate);
        return koreanLunar.getSolarDate();
    }

    public LocalDate calculateLunarDate(final LocalDate date, final String type) {
        if (LUNAR.equals(type)) {
            return getCurLunarDate(date);
        }

        if (SOLAR.equals(type)) {
            koreanLunar.setSolarDate(date);
            return getCurLunarDate(koreanLunar.getLunarDate());
        }
        throw new IllegalArgumentException("잘못된 타입입니다.");
    }

    private LocalDate getCurLunarDate(final LocalDate date) {
        return LocalDate.of(LocalDate.now().getYear(), date.getMonth(), date.getDayOfMonth());
    }
}
