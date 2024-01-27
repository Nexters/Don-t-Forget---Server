package com.dontforget.dontforget.infra.anniversary.repository;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.exception.NotFoundAnniversaryException;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.infra.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.mapper.AnniversaryMapper;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import com.dontforget.dontforget.infra.notice.NoticeEntity;
import com.dontforget.dontforget.infra.notice.repository.NoticeEntityRepository;
import java.util.List;

public class AnniversaryRepositoryImpl implements AnniversaryRepository {

    private final AnniversaryEntityRepository anniversaryEntityRepository;
    private final NoticeEntityRepository noticeEntityRepository;
    private final AnniversaryMapper anniversaryMapper;
    private final NoticeMapper noticeMapper;

    public AnniversaryRepositoryImpl(AnniversaryEntityRepository anniversaryEntityRepository,
        NoticeEntityRepository noticeEntityRepository, AnniversaryMapper anniversaryMapper,
        NoticeMapper noticeMapper) {
        this.anniversaryEntityRepository = anniversaryEntityRepository;
        this.noticeEntityRepository = noticeEntityRepository;
        this.anniversaryMapper = anniversaryMapper;
        this.noticeMapper = noticeMapper;
    }


    @Override
    public Long save(final Anniversary anniversary) {
        final AnniversaryEntity anniversaryEntity = anniversaryMapper.toEntity(anniversary);
        final Long anniversaryId = anniversaryEntityRepository.save(anniversaryEntity)
            .getId();

        final List<NoticeEntity> noticeEntities = anniversary.getNotices()
            .stream()
            .map(it -> noticeMapper.toEntity(it,anniversaryId))
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
            .map(noticeMapper::toDomain)
            .toList();

        return anniversaryMapper.toDomain(anniversaryEntity, notices);
    }

    @Override
    public List<AnniversaryEntity> findByDeviceUuidOrderByRecentDate(final String deviceId) {
        return  anniversaryEntityRepository.findByDeviceUuidOrderByRecentDate(deviceId);
    }
}