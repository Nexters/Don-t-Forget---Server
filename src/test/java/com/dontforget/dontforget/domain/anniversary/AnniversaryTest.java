package com.dontforget.dontforget.domain.anniversary;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import autoparams.AutoSource;
import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.enums.NoticeType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Anniversary 도메인 테스트")
class AnniversaryTest {

    @AutoSource
    @ParameterizedTest
    void 기념일이_정상적으로_생성된다(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final List<NoticeType> alarmSchedule,
        final CardType cardType
    ) {
        // given
        final CalendarType type = CalendarType.SOLAR;
        final CalendarCalculator calendarCalculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());
        int year = LocalDate.now().getYear();
        // when
        final Anniversary anniversary = Anniversary.create(
            deviceUuid,
            title,
            date,
            content,
            type,
            alarmSchedule,
            cardType,
            calendarCalculator
        );

        // then

        assertAll(
            () -> assertThat(anniversary).isNotNull(),
            () -> assertThat(anniversary.getDeviceUuid()).isEqualTo(deviceUuid),
            () -> assertThat(anniversary.getTitle()).isEqualTo(title),
            () -> assertThat(anniversary.getContent()).isEqualTo(content),
            () -> assertThat(anniversary.getLunarDate()).isEqualTo(
                calendarCalculator.calculateLunarDate(date, type, year)),
            () -> assertThat(anniversary.getSolarDate()).isEqualTo(
                calendarCalculator.calculateSolarDate(date, type, year)),
            () -> assertThat(anniversary.getCardType()).isEqualTo(cardType)
        );

        final List<Notice> notices = alarmSchedule
            .stream()
            .map(it -> new Notice(null, it))
            .toList();
        assertThat(anniversary.getNotices())
            .usingRecursiveComparison()
            .isEqualTo(notices);
    }

    @ParameterizedTest
    @CsvSource(value = "2024-02-14:2025-03-13", delimiter = ':')
    void 다음년도_양력_날짜를_올바르게_계산한다(
        final LocalDate lunarDate,
        final LocalDate expectedDate
    ) {
        // given
        final Anniversary anniversary = new Anniversary(
            1L,
            "title",
            "content",
            "deviceUuid",
            LocalDate.now(),
            "SOLAR",
            lunarDate,
            LocalDate.now(),
            List.of(), CardType.ARM);
        final CalendarType type = CalendarType.LUNAR;
        final CalendarCalculator calculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());

        // when
        Anniversary nextAnniversary = Anniversary.createNextAnniversary(anniversary, calculator);

        // then
        assertThat(nextAnniversary.getSolarDate()).isEqualTo(expectedDate);
    }
}
