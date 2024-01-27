package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import autoparams.AutoSource;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.anniversary.service.CreateAnniversary;
import com.dontforget.dontforget.domain.notice.NoticeType;
import com.dontforget.dontforget.infra.notice.NoticeEntity;
import com.dontforget.dontforget.infra.notice.repository.NoticeEntityRepository;
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

    @DisplayName("기념일을 정상적으로 생성하면, 그에 따라 알림이 정상적으로 생성된다.")
    @AutoSource
    @ParameterizedTest
    void sut_create_anniversary_success(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final List<NoticeType> notices
    ) {
        // given
        final CreateAnniversaryQuery query = new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, "solar", notices
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

    @DisplayName("타입(solar, lunar) 이 올바르지 않은 경우, 예외가 발생한다.")
    @ParameterizedTest
    @AutoSource
    void sut_create_anniversary_fail_when_type_is_invalid(
        final String deviceUuid,
        final String title,
        final LocalDate date,
        final String content,
        final List<NoticeType> notices
    ) {
        // given
        final String type = "invalid";
        final CreateAnniversaryQuery query = new CreateAnniversaryQuery(
            deviceUuid, title, date,
            content, type, notices
        );
        final CalendarCalculator calculator = new CalendarCalculator(
            new KoreanLunarCalendarCalculator());
        final CreateAnniversary sut = new CreateAnniversary(anniversaryRepository, calculator);

        // when & then
        assertThatThrownBy(() -> sut.create(query))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("잘못된 타입입니다.");
    }
}
