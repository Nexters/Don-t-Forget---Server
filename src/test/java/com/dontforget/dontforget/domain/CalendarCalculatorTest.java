package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;


@DisplayName("CalenderCalculator 테스트")
class CalendarCalculatorTest {

    @Test
    void 타입이_음력_또는_양력이_아닐_경우_예외를_던진다() {
        // given
        final CalendarType type = null;
        final LocalDate localDate = LocalDate.of(2024, 1, 1);
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());
        int year = LocalDate.now().getYear();
        // when & then
        assertThatCode(() -> sut.calculateSolarDate(localDate, type, year))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("CalendarType이 잘못된 타입입니다.");
    }

    @ParameterizedTest
    @EnumSource(value = CalendarType.class)
    void 타입이_음력_또는_양력일_경우_정상적으로_작동한다(final CalendarType type) {
        // given
        final LocalDate localDate = LocalDate.of(2024, 1, 1);
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());
        final int year = LocalDate.now().getYear();
        // when & then
        assertThatCode(() -> sut.calculateSolarDate(localDate, type, year))
            .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @CsvSource(value = "2000-02-18:2024-03-27", delimiter = ':')
    void 양력_변환_테스트_타입이_음력일_경우_현재의_양력_날짜로_변환한다(
        final LocalDate lunarDate,
        final LocalDate expectedDate
    ) {
        // given
        final CalendarType type = CalendarType.LUNAR;
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());
        final int year = LocalDate.now().getYear();

        // when
        final LocalDate convertDateTime = sut.calculateSolarDate(lunarDate, type, year);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-03-23:2024-03-27", delimiter = ':')
    void 양력_변환_테스트_타입이_양력일_경우_음력으로_변환_후_현재의_양력_날짜로_변환한다(
        final LocalDate solarDate, final LocalDate expectedDate
    ) {
        // given
        final CalendarType type = CalendarType.SOLAR;
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());
        final int year = LocalDate.now().getYear();

        // when
        final LocalDate convertDateTime = sut.calculateSolarDate(solarDate, type, year);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-03-23:2024-02-18", delimiter = ':')
    void 음력_변환_테스트_타입이_양력일_경우_현재의_음력_날짜로_변환한다(
        final LocalDate solarDate,
        final LocalDate expectedDate
    ) {
        // given
        final CalendarType type = CalendarType.SOLAR;
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());
        final int year = LocalDate.now().getYear();

        // when
        final LocalDate convertDateTime = sut.calculateLunarDate(solarDate, type, year);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-02-18:2024-02-18", delimiter = ':')
    void 음력_변환_테스트_타입이_음력일_경우_현재의_음력_날짜로_변환한다(
        final LocalDate lunarDate,
        final LocalDate expectedDate
    ) {
        // given
        final CalendarType type = CalendarType.LUNAR;
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());
        final int year = LocalDate.now().getYear();

        // when
        final LocalDate convertDateTime = sut.calculateLunarDate(lunarDate, type, year);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

}
