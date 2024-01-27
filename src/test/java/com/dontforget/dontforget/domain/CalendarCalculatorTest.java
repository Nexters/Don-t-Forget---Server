package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayName("CalenderCalculator 테스트")
class CalendarCalculatorTest {

    @Test
    @DisplayName("타입이 음력 또는 양력이 아닐 경우, 예외를 던진다.")
    void sut_convert_fail_if_type_invalid() {
        // given
        final String type = "wrongType";
        final LocalDate localDate = LocalDate.of(2024, 1, 1);
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());

        // when & then
        assertThatCode(() -> sut.calculateSolarDate(localDate, type))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("잘못된 타입입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"lunar", "solar"})
    @DisplayName("타입이 음력 또는 양력일 경우, 정상적으로 작동한다.")
    void sut_convert_success_if_type_is_valid(final String type) {
        // given
        final LocalDate localDate = LocalDate.of(2024, 1, 1);
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());

        // when & then
        assertThatCode(() -> sut.calculateSolarDate(localDate, type))
            .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @CsvSource(value = "2000-02-18:2024-03-27", delimiter = ':')
    @DisplayName("양력 변환 테스트 : 타입이 음력일 경우 현재의 양력 날짜로 변환한다")
    void sut_convert_solar_date_if_type_is_solar_date(
        final LocalDate lunarDate,
        final LocalDate expectedDate
    ) {
        // given
        final String type = "lunar";
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());

        // when
        final LocalDate convertDateTime = sut.calculateSolarDate(lunarDate, type);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-03-23:2024-03-27", delimiter = ':')
    @DisplayName("양력 변환 테스트 : 타입이 양력일 경우 음력으로 변환 후, 현재의 양력 날짜로 변환한다")
    void sut_convert_solar_date_if_type_is_lunar_date(
        final LocalDate solarDate, final LocalDate expectedDate
    ) {
        // given
        final String type = "solar";
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());

        // when
        final LocalDate convertDateTime = sut.calculateSolarDate(solarDate, type);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-03-23:2024-02-18", delimiter = ':')
    @DisplayName("음력 변환 테스트 : 타입이 양력일 경우, 현재 음력 날짜로 변환한다")
    void sut_convert_lunar_date_if_type_is_solar_date(
        final LocalDate solarDate,
        final LocalDate expectedDate
    ) {
        // given
        final String type = "solar";
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());

        // when
        final LocalDate convertDateTime = sut.calculateLunarDate(solarDate, type);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-02-18:2024-02-18", delimiter = ':')
    @DisplayName("음력 변환 테스트 : 타입이 음력일 경우, 현재 음력 날짜로 변환한다")
    void sut_convert_lunar_date_if_type_is_lunar_date(
        final LocalDate lunarDate,
        final LocalDate expectedDate
    ) {
        // given
        final String type = "lunar";
        final CalendarCalculator sut = new CalendarCalculator(new KoreanLunarCalendarCalculator());

        // when
        final LocalDate convertDateTime = sut.calculateLunarDate(lunarDate, type);

        // then
        assertThat(convertDateTime).isEqualTo(expectedDate);
    }
}
