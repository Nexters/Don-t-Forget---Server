package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("KoreanLunarCalendarCalculator 테스트")
class KoreanLunarCalendarCalculatorTest {

    @ParameterizedTest
    @CsvSource(value = "2000-03-23:2000-02-18", delimiter = ':')
    void 양력일_경우_음력_날짜로_변환한다(
        final LocalDate jonghaSolarBirthday,
        final LocalDate expectedDate
    ) {
        // given
        final KoreanLunarCalendarCalculator sut = new KoreanLunarCalendarCalculator();

        // when
        LocalDate actual = sut.convertLunarDateFromSolarDate(jonghaSolarBirthday);

        // then
        assertThat(actual).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource(value = "2000-02-18:2000-03-23", delimiter = ':')
    void 음력일_경우_양력_날짜로_변환한다(
        final LocalDate lunarDate,
        final LocalDate expectedDate
    ) {
        // given
        final KoreanLunarCalendarCalculator sut = new KoreanLunarCalendarCalculator();

        // when
        final LocalDate actual = sut.convertSolarDateFromLunarDate(lunarDate);

        // then
        assertThat(actual).isEqualTo(expectedDate);
    }

    @Test
    void 입력된_타입이_정확하지_않으면_에러를_던진다() {
        // given
        final KoreanLunarCalendarCalculator sut = new KoreanLunarCalendarCalculator();
        final LocalDate wrongDate = LocalDate.of(2500, 1, 1);

        // when
        assertThatCode(() -> sut.convertLunarDateFromSolarDate(wrongDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("날짜의 범위가 잘못되었습니다.");
    }
}
