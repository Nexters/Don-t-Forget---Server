package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import autoparams.AutoSource;
import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.notice.enums.NoticeType;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeEntityRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DisplayName("CreateAnniversary 도메인 서비스 테스트")
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class CreateAnniversaryTest {

    @Autowired
    private AnniversaryRepository anniversaryRepository;

    @Autowired
    private NoticeEntityRepository noticeEntityRepository;

    @AutoSource
    @ParameterizedTest
    void 기념일을_정상적으로_생성하면_그에_따라_알림이_정상적으로_생성된다(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final CardType cardType,
        final List<NoticeType> notices
    ) {
        // given
        final CreateAnniversaryQuery query = new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, CalendarType.SOLAR, cardType, notices
        );
        final CalendarCalculator calculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());
        final CreateAnniversary sut = new CreateAnniversary(anniversaryRepository, calculator);

        // when
        final Long anniversaryId = sut.create(query);

        // then
        final Anniversary anniversary = anniversaryRepository.findById(anniversaryId);
        final List<NoticeEntity> noticeEntities = noticeEntityRepository.findAllByAnniversaryId(
            anniversaryId);

        assertThat(noticeEntities)
            .usingRecursiveComparison()
            .isEqualTo(anniversary.getNotices());

        assertThat(anniversary.getId()).isEqualTo(anniversaryId);
        assertThat(anniversary.getDeviceUuid()).isEqualTo(deviceUuid);
        assertThat(anniversary.getTitle()).isEqualTo(title);
        assertThat(anniversary.getContent()).isEqualTo(content);
    }

    @ParameterizedTest
    @AutoSource
    void 타입이_올바르지_않으면_예외가_발생한다(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final CardType cardType,
        final List<NoticeType> notices
    ) {
        // given
        final CalendarType type = null;
        final CreateAnniversaryQuery query = new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, type, cardType, notices
        );
        final CalendarCalculator calculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());
        final CreateAnniversary sut = new CreateAnniversary(anniversaryRepository, calculator);

        // when & then
        assertThatThrownBy(() -> sut.create(query))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("CalendarType이 잘못된 타입입니다.");
    }
}
