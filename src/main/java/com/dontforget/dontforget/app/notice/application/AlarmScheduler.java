package com.dontforget.dontforget.app.notice.application;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.service.CalendarCalculator;
import com.dontforget.dontforget.domain.notice.NoticeTarget;
import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.domain.notice.service.AlarmSearcher;
import com.dontforget.dontforget.domain.notice.service.AlarmSender;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeEntityRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmScheduler {

    private final AlarmSearcher searcher;
    private final AlarmSender sender;
    private final NoticeEntityRepository noticeEntityRepository;
    private final AnniversaryRepository anniversaryRepository;
    private final CalendarCalculator calendarCalculator;

    @Scheduled(cron = "* 0 9 * * *")
    @Transactional
    public void run() {
        List<NoticeTarget> alarmTargets = searcher.findAlarmTargets();

        sendNotifications(alarmTargets);
        updateNotificationStatus(alarmTargets);
        updateNextAnniversary(alarmTargets);
        resetNotificationStatus(alarmTargets);
    }

    private void sendNotifications(List<NoticeTarget> alarmTargets) {
        alarmTargets.forEach(it -> sender.send(it.getDeviceUuid(), it.getTitle(), it.getMessage()));
    }

    private void updateNotificationStatus(List<NoticeTarget> alarmTargets) {
        List<Long> beSendNoticeIds = alarmTargets.stream()
            .map(NoticeTarget::getNoticeId)
            .toList();
        noticeEntityRepository.updateAllById(NoticeStatus.BE_SEND, beSendNoticeIds);
    }

    private void updateNextAnniversary(List<NoticeTarget> alarmTargets) {
        List<NoticeTarget> endAnniversary = alarmTargets.stream()
            .filter(NoticeTarget::isDDay)
            .toList();

        List<Anniversary> updateAnniversary = endAnniversary.stream()
            .map(it -> Anniversary.createNextAnniversary(it.getAnniversary(), calendarCalculator))
            .toList();

        anniversaryRepository.saveAll(updateAnniversary);
    }

    private void resetNotificationStatus(List<NoticeTarget> alarmTargets) {
        Set<Long> updateAnniversaryIds = alarmTargets.stream()
            .filter(NoticeTarget::isDDay)
            .map(it -> it.getAnniversary().getId())
            .collect(Collectors.toSet());
        noticeEntityRepository.updateAllByAnniversaryId(NoticeStatus.WAITING_SEND,
            updateAnniversaryIds);
    }
}
