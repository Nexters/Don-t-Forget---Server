package com.dontforget.dontforget.app.anniversary.application.service;

import com.dontforget.dontforget.app.anniversary.application.query.CreateAnniversaryQuery;
import com.dontforget.dontforget.common.DomainService;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CreateAnniversary {

    private final AnniversaryRepository anniversaryRepository;
    private final CalendarCalculator calendarCalculator;

    public Long create(final CreateAnniversaryQuery query) {
        final Anniversary anniversary = Anniversary.create(
                query.getDeviceUuid(),
                query.getTitle(),
                query.getDate(),
                query.getContent(),
                query.getType(),
                query.getAlarmSchedule(),
                calendarCalculator
        );

        return anniversaryRepository.save(anniversary);
    }
}
