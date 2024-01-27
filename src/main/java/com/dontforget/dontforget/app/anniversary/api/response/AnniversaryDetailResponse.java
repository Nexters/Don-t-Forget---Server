package com.dontforget.dontforget.app.anniversary.api.response;

import java.time.LocalDate;
import java.util.List;

public record AnniversaryDetailResponse(
    Long anniversaryId, String title, LocalDate lunarDate,
    LocalDate solarDate, List<String> alarmSchedule, String content,
    String deviceId) {
}
