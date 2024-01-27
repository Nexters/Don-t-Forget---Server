package com.dontforget.dontforget.infra.anniversary.repository;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.exception.NotFoundAnniversaryException;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.notice.NoticeEntity;
import com.dontforget.dontforget.infra.notice.repository.NoticeEntityRepository;

import java.util.List;

public class AnniversaryRepositoryImpl implements AnniversaryRepository {

    private final AnniversaryEntityRepository anniversaryEntityRepository;
    private final NoticeEntityRepository noticeEntityRepository;

    public AnniversaryRepositoryImpl(
        final AnniversaryEntityRepository anniversaryEntityRepository,
        final NoticeEntityRepository noticeEntityRepository
    ) {
        this.anniversaryEntityRepository = anniversaryEntityRepository;
        this.noticeEntityRepository = noticeEntityRepository;
    }

    @Override
    public Long save(final Anniversary anniversary) {
        final AnniversaryEntity anniversaryEntity = AnniversaryEntity.from(anniversary);
        final Long anniversaryId = anniversaryEntityRepository.save(anniversaryEntity)
            .getId();

        final List<NoticeEntity> noticeEntities = anniversary.getNotices()
            .stream()
            .map(it -> NoticeEntity.of(it, anniversaryId))
            .toList();

        noticeEntityRepository.saveAll(noticeEntities);
        return anniversaryEntity.getId();
    }

    @Override
    public Anniversary findById(final Long anniversaryId) {
        final AnniversaryEntity anniversaryEntity = anniversaryEntityRepository.findById(
                anniversaryId)
            .orElseThrow(() -> new NotFoundAnniversaryException(anniversaryId));

        final List<Notice> notices = noticeEntityRepository.findAllByAnniversaryId(anniversaryId)
            .stream()
            .map(NoticeEntity::toDomain)
            .toList();

        return anniversaryEntity.toDomain(notices);
    }
}
