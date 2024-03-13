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
                calendarCalculator.calculateCurLunarDate(date, type)),
            () -> assertThat(anniversary.getSolarDate()).isEqualTo(
                calendarCalculator.calculateCurSolarDate(date, type)),
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
}
