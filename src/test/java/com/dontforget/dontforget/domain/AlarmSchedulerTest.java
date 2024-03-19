package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import autoparams.AutoSource;
import com.dontforget.dontforget.app.notice.application.AlarmScheduler;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.config.FakeAlarmSender;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.domain.notice.enums.NoticeType;
import com.dontforget.dontforget.domain.notice.service.AlarmSearcher;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DisplayName("AlarmScheduler 테스트")
@ContextConfiguration(classes = {RepositoryTestConfig.class})
@DataJpaTest
class AlarmSchedulerTest {

    private final KoreanLunarCalendarCalculator koreaCalculator = new KoreanLunarCalendarCalculator();
    private final CalendarCalculator calculator = new CalendarCalculator(koreaCalculator);


    @ParameterizedTest
    @AutoSource
    void 알림이_돌면_알림의_상태가_변경된다(
        final List<Notice> notices
    ) {
        // given
        var anniversaryStub = Mockito.mock(AnniversaryRepository.class);
        var noticeStub = Mockito.mock(NoticeRepository.class);
        when(noticeStub.findAllByAnniversaryId(any()))
            .thenReturn(notices);

        var alarmSearcher = new AlarmSearcher(anniversaryStub);
        var alarmSender = new FakeAlarmSender();

        // when
        var sut = new AlarmScheduler(
            anniversaryStub,
            noticeStub,
            calculator,
            alarmSearcher,
            alarmSender
        );

        // then
        sut.run();

        // then
        verify(noticeStub, times(2)).updateNoticeStatus(any(NoticeStatus.class), anyList());
    }


    @Autowired
    private AnniversaryRepository anniversaryRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void 알림이_돌때_디데이일경우_다음_기념일을_생성한다() {
        Long anniversaryId = anniversaryRepository.save(dDayAnniversary);
        var alarmSearcher = new AlarmSearcher(anniversaryRepository);
        var alarmSender = new FakeAlarmSender();
        var sut = new AlarmScheduler(
            anniversaryRepository,
            noticeRepository,
            calculator,
            alarmSearcher,
            alarmSender
        );
        // when
        sut.run();

        // then
        var actual = anniversaryRepository.findById(anniversaryId);
        var expected = Anniversary.createNextAnniversary(dDayAnniversary, calculator);

        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id", "notices.id", "notices.anniversaryId")
            .isEqualTo(expected);
    }

    @Test
    void 알림이_돌때_알림타깃이고_디데이인경우_알람_상태는_WAITING_SEND이다() {
        Long anniversaryId = anniversaryRepository.save(dDayAnniversary);
        var alarmSearcher = new AlarmSearcher(anniversaryRepository);
        var alarmSender = new FakeAlarmSender();
        var sut = new AlarmScheduler(
            anniversaryRepository,
            noticeRepository,
            calculator,
            alarmSearcher,
            alarmSender
        );

        // when
        sut.run();

        // then
        var actual = noticeRepository.findAllByAnniversaryId(anniversaryId);
        for (Notice notice : actual) {
            assertThat(notice.getNoticeStatus()).isEqualTo(NoticeStatus.WAITING_SEND);
        }
    }

    @Test
    void 알림이_돌때_알림타깃이고_디데이가_아닌경우_알람_상태는_BE_SEND이다() {
        Long anniversaryId = anniversaryRepository.save(notDDayAnniversary);
        var alarmSearcher = new AlarmSearcher(anniversaryRepository);
        var alarmSender = new FakeAlarmSender();
        var sut = new AlarmScheduler(
            anniversaryRepository,
            noticeRepository,
            calculator,
            alarmSearcher,
            alarmSender
        );

        // when
        sut.run();

        // then
        var actual = noticeRepository.findAllByAnniversaryId(anniversaryId);
        for (Notice notice : actual) {
            assertThat(notice.getNoticeStatus()).isEqualTo(NoticeStatus.BE_SEND);
        }
    }

    private final Anniversary dDayAnniversary = new Anniversary(
        "title",
        "content",
        "deviceUuid",
        LocalDate.now(),
        "SOLAR",
        LocalDate.now(),
        LocalDate.now(),
        List.of(new Notice(null, NoticeType.D_DAY, NoticeStatus.WAITING_SEND)),
        CardType.ARM
    );

    private final Anniversary notDDayAnniversary = new Anniversary(
        "title",
        "content",
        "deviceUuid",
        LocalDate.now(),
        "SOLAR",
        LocalDate.now().plusDays(1L),
        LocalDate.now().plusDays(1L),
        List.of(new Notice(null, NoticeType.ONE_DAYS, NoticeStatus.WAITING_SEND)),
        CardType.ARM
    );
}
