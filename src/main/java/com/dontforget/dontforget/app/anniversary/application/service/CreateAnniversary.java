package com.dontforget.dontforget.app.anniversary.application.service;

import com.dontforget.dontforget.app.anniversary.api.request.AnniversaryCreateRequest;
import com.dontforget.dontforget.app.anniversary.application.query.CreateQuery;
import com.dontforget.dontforget.app.anniversary.application.service.mapper.AnniversaryMapper;
import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.anniversary.repository.AnniversaryEntityRepository;
import com.dontforget.dontforget.infra.notice.repository.NoticeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAnniversary {

  private final AnniversaryEntityRepository anniversaryRepository;
  private final NoticeEntityRepository noticeRepository;
  private final AnniversaryMapper mapper;

  @Transactional
  public Long create(CreateQuery query) {
    AnniversaryEntity savedAnniversary = anniversaryRepository.save(
        mapper.toAnniversaryEntity(query));

    createNotice(query, savedAnniversary);
    return savedAnniversary.getId();
  }

  private void createNotice(CreateQuery query, AnniversaryEntity savedAnniversary) {
    noticeRepository.saveAll(mapper.toNoticeEntities(savedAnniversary.getId(), query));
  }
}
