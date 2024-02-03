package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import autoparams.AutoSource;
import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.query.UpdateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.anniversary.service.UpdateAnniversary;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;


@DataJpaTest
@DisplayName("CreateAnniversary 도메인 서비스 테스트")
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class UpdateAnniversaryTest {

    @Autowired
    private AnniversaryRepository anniversaryRepository;

    @ParameterizedTest
    @AutoSource
    void 기념일을_정상적으로_수정하면_그에_따라_알림이_정상적으로_수정된다(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final List<NoticeType> notices,
        final String updatedTitle,
        final LocalDate updatedDate,
        final String updatedContent,
        final List<NoticeType> updatedNotices,
        final CardType cardType
    ) {
        // given
        final CalendarType type = CalendarType.SOLAR;
        final CalendarCalculator calculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());
        final CreateAnniversaryQuery query = new CreateAnniversaryQuery(
            deviceUuid, title, date, content, type, cardType, notices);

        final CreateAnniversary createAnniversary = new CreateAnniversary(anniversaryRepository,
            calculator);
        final Long anniversaryId = createAnniversary.create(query);

        final UpdateAnniversaryQuery updateQuery = new UpdateAnniversaryQuery(
            anniversaryId, updatedTitle, updatedDate, type, updatedNotices, updatedContent
        );
        final UpdateAnniversary sut = new UpdateAnniversary(anniversaryRepository, calculator);

        // when
        sut.updateAnniversary(updateQuery);

        // then
        final Anniversary anniversary = anniversaryRepository.findById(anniversaryId);

        assertAll(
            () -> assertThat(anniversary.getTitle()).isEqualTo(updatedTitle),
            () -> assertThat(anniversary.getContent()).isEqualTo(updatedContent),
            () -> assertThat(anniversary.getLunarDate()).isEqualTo(
                calculator.calculateLunarDate(updatedDate, type)),
            () -> assertThat(anniversary.getSolarDate()).isEqualTo(
                calculator.calculateSolarDate(updatedDate, type)),
            () -> assertThat(anniversary.getNotices().stream()
                .map(Notice::getNoticeType).toList())
                .usingRecursiveComparison()
                .isEqualTo(updatedNotices)
        );
    }
}
