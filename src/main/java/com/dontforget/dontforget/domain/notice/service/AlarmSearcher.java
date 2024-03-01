package com.dontforget.dontforget.domain.notice.service;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.notice.NoticeTarget;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmSearcher {

    private final AnniversaryRepository anniversaryRepository;

    public List<NoticeTarget> findAlarmTargets() {
        LocalDate today = LocalDate.now();
        return anniversaryRepository.findAll().stream()
            .flatMap(anniversary -> getSendMessageGroup(anniversary, today).stream())
            .collect(Collectors.toList());
    }

    private List<NoticeTarget> getSendMessageGroup(Anniversary anniversary, LocalDate today) {
        return anniversary.getNotices().stream()
            .filter(notice -> notice.isSendNotice(today, anniversary.getSolarDate()))
            .map(notice -> NoticeTarget.of(anniversary, notice))
            .collect(Collectors.toList());
    }
}
