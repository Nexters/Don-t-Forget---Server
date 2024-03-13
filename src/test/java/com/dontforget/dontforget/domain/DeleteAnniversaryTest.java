package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import autoparams.AutoSource;
import com.dontforget.dontforget.common.CalendarType;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.exception.NotFoundAnniversaryException;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.anniversary.service.DeleteAnniversary;
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


@DataJpaTest
@DisplayName("DeleteAnniversary 도메인 서비스 테스트")
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class DeleteAnniversaryTest {

    @Autowired
    private AnniversaryRepository anniversaryRepository;

    @Autowired
    private NoticeEntityRepository noticeEntityRepository;

    @ParameterizedTest
    @AutoSource
    void 기념일을_정상적으로_삭제하면_그에_따라_기념일과_알림이_정상적으로_삭제된다(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final List<NoticeType> notices,
        final CardType cardType
    ) {
        // given
        final CalendarType type = CalendarType.SOLAR;
        final CreateAnniversaryQuery query = new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, type, cardType, notices
        );
        final CalendarCalculator calculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());
        final CreateAnniversary createAnniversary = new CreateAnniversary(
            anniversaryRepository, calculator);
        final Long anniversaryId = createAnniversary.create(query);

        final DeleteAnniversary sut = new DeleteAnniversary(anniversaryRepository);

        // when
        sut.deleteAnniversary(anniversaryId);
        // then
        final List<NoticeEntity> noticeEntities = noticeEntityRepository.findAllByAnniversaryId(
            anniversaryId);
        assertThat(noticeEntities).isEmpty();

        assertThatCode(() -> anniversaryRepository.findById(anniversaryId))
            .isInstanceOf(NotFoundAnniversaryException.class)
            .hasMessage("id가 " + anniversaryId + " 인 기념일은 존재하지 않습니다.");
    }

}
