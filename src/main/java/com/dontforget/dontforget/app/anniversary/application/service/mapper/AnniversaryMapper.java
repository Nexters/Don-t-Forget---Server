package com.dontforget.dontforget.app.anniversary.application.service.mapper;

import com.dontforget.dontforget.app.anniversary.application.query.CreateAnnivarsaryQuery;
import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.notice.NoticeEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnniversaryMapper {

  public AnniversaryEntity toAnniversaryEntity(CreateAnnivarsaryQuery query) {
    return new AnniversaryEntity(
        query.getTitle(),
        query.getContent(),
        query.getDeviceUuid(),
        query.getDate(), // : todo 음력, 양력 계산해서 넣는 로직으로 변환해야함.
        query.getDate());
  }

  public List<NoticeEntity> toNoticeEntities(Long anniversaryId, CreateAnnivarsaryQuery query) {
    return query.getAlarmSchedule().stream()
        .map(it -> new NoticeEntity(anniversaryId, it))
        .toList();
  }
}
