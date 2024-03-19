package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import autoparams.AutoSource;
import com.dontforget.dontforget.app.notice.application.AlarmScheduler;
import com.dontforget.dontforget.common.KoreanLunarCalendarCalculator;
import com.dontforget.dontforget.config.FakeAlarmSender;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.domain.notice.service.AlarmSearcher;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DisplayName("AlarmScheduler 테스트")
@ContextConfiguration(classes = {RepositoryTestConfig.class})
@DataJpaTest
class AlarmSchedulerTest {

    private CalendarCalculator calculator = new CalendarCalculator(
        new KoreanLunarCalendarCalculator());
    @Autowired
    private NoticeRepository noticeRepository;

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

    @ParameterizedTest
    @AutoSource
    void 알림의_상태가_변경된다(
        final Long anniversaryId,
        final List<Notice> notices
    ) {
        // given
        List<NoticeEntity> noticeEntities = noticeRepository.saveAll(anniversaryId, notices);
        List<Long> ids = noticeEntities
            .stream()
            .map(NoticeEntity::getId)
            .toList();

        // when
        noticeRepository.updateNoticeStatus(NoticeStatus.BE_SEND, ids);

        // then
        List<Notice> findNotices = noticeRepository.findAllByAnniversaryId(anniversaryId);
        for (Notice notice : findNotices) {
            assertThat(notice.getNoticeStatus()).isEqualTo(NoticeStatus.BE_SEND);
        }
    }
}
