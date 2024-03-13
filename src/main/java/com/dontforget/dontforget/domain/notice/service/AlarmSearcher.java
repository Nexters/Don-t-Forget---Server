package com.dontforget.dontforget.domain.notice.service;

import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.notice.NoticeTarget;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class AlarmSearcher {

    private final AnniversaryRepository anniversaryRepository;

    public List<NoticeTarget> findAlarmTargets(LocalDate time) {
        return anniversaryRepository.findAll()
            .stream()
            .flatMap(anniversary -> getSendableNotices(anniversary, time).stream())
            .toList();
    }

    private List<NoticeTarget> getSendableNotices(
        Anniversary anniversary,
        LocalDate time
    ) {
        return anniversary
            .getNotices()
            .stream()
            .filter(notice -> notice.isSendable(time, anniversary.getSolarDate()))
            .map(notice -> NoticeTarget.of(anniversary, notice))
            .toList();
    }
}
