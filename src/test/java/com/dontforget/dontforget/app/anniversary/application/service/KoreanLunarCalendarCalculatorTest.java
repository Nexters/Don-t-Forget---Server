package com.dontforget.dontforget.app.anniversary.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DisplayName("KoreanLunarCalendarCalculator 테스트")
class KoreanLunarCalendarCalculatorTest {
    @ParameterizedTest
    @CsvSource(value = "2000-03-23:2000-02-18", delimiter = ':')
    @DisplayName("양력일 경우, 음력 날짜로 변환한다.")
    void sut_convert_lunar_date_if_type_is_solar_date(
            final LocalDate jonghaSolarBirthday,
            final String expectedDate
    ) {
        // given
        final KoreanLunarCalendarCalculator sut = new KoreanLunarCalendarCalculator();

        // when
        sut.setSolarDate(jonghaSolarBirthday.getYear(), jonghaSolarBirthday.getMonthValue(), jonghaSolarBirthday.getDayOfMonth());
        String actual = sut.getLunarIsoFormat();
        // then
        assertThat(actual).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-02-18:2000-03-23", delimiter = ':')
    @DisplayName("음력일 경우, 양력 날짜로 변환한다.")
    void sut_convert_solar_date_if_type_is_lunar_date(
            final LocalDate lunarDate,
            final String expectedDate
    ) {
        // given
        final KoreanLunarCalendarCalculator sut = new KoreanLunarCalendarCalculator();

        // when
        sut.setLunarDate(lunarDate.getYear(), lunarDate.getMonthValue(), lunarDate.getDayOfMonth());
        final String actual = sut.getSolarIsoFormat();

        // then
        assertThat(actual).isEqualTo(expectedDate);
    }

    @Test
    @DisplayName("입력된 타입이 정확하지 않으면 에러를 던진다")
    void sut_throw_exception_if_type_is_not_correct() {
        // given
        final KoreanLunarCalendarCalculator sut = new KoreanLunarCalendarCalculator();

        // when
        assertThatCode(() -> sut.setSolarDate(-1,-1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("날짜의 범위가 잘못되었습니다.");
    }
}