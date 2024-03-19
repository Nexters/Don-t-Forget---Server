package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.domain.notice.NoticeTarget;
import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.domain.notice.service.AlarmSearcher;
import com.dontforget.dontforget.domain.notice.service.AlarmSender;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmScheduler {

    private final AnniversaryRepository anniversaryRepository;
    private final NoticeRepository noticeRepository;

    private final CalendarCalculator calendarCalculator;

    private final AlarmSearcher searcher;
    private final AlarmSender sender;

    @Scheduled(cron = "* 0 9 * * *")
    @Transactional
    public void run() {
        // 1. 알람 대상 조회
        var expectedNotices = searcher.findAlarmTargets(LocalDate.now());
        // 2. 알람 발송
        sender.sendNotifications(expectedNotices);
        // 3. 알람 상태 변경
        updateNotificationStatus(expectedNotices);
        // 4. D-Day라면 다음 기념일 설정하고 create 하는 로직 5. D-Day가 아니라면 알람 상태를 변경한다.
        updateDdayAnniversary(expectedNotices);
    }

    private void updateNotificationStatus(final List<NoticeTarget> alarmTargets) {
        var noticeIds = alarmTargets
            .stream()
            .map(NoticeTarget::getNoticeId)
            .toList();
        noticeRepository.updateNoticeStatus(NoticeStatus.BE_SEND, noticeIds);
    }

    private void updateDdayAnniversary(final List<NoticeTarget> alarmTargets) {
        var anniversaries = alarmTargets.stream()
            .filter(NoticeTarget::isDDay)
            .map(NoticeTarget::getAnniversary)
            .distinct()
            .toList();

        var nextAnniversaries = anniversaries.stream()
            .map(it -> Anniversary.createNextAnniversary(it, calendarCalculator))
            .toList();
        anniversaryRepository.saveAll(nextAnniversaries);

        List<Long> noticeIds = anniversaries.stream().flatMap(it -> it.getNotices().stream())
            .map(Notice::getId)
            .toList();
        noticeRepository.updateNoticeStatus(NoticeStatus.WAITING_SEND, noticeIds);
    }
}
