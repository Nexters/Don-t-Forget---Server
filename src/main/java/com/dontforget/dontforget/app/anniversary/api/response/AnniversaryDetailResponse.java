package com.dontforget.dontforget.app.anniversary.api.response;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import java.time.LocalDate;
import java.util.List;

public record AnniversaryDetailResponse(
    Long anniversaryId, String title, LocalDate lunarDate,
    LocalDate solarDate, List<String> alarmSchedule,
    String content, String deviceId

) {

    public static AnniversaryDetailResponse from(final Anniversary anniversary) {
        return new AnniversaryDetailResponse(
            anniversary.getId(),
            anniversary.getTitle(),
            anniversary.getLunarDate(),
            anniversary.getSolarDate(),
            anniversary.getNotices()
                .stream()
                .map(it -> it.getNoticeType().toString())
                .toList(),
            anniversary.getContent(),
            anniversary.getDeviceUuid()
        );
    }
}
