package com.dontforget.dontforget.infra.jpa;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.exception.NotFoundAnniversaryException;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.infra.jpa.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.jpa.anniversary.repository.AnniversaryEntityRepository;
import com.dontforget.dontforget.infra.mapper.AnniversaryMapper;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeEntityRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AnniversaryRepositoryImpl implements AnniversaryRepository {

    private final AnniversaryEntityRepository anniversaryEntityRepository;
    private final NoticeEntityRepository noticeEntityRepository;
    private final AnniversaryMapper anniversaryMapper;
    private final NoticeMapper noticeMapper;

    public AnniversaryRepositoryImpl(
        AnniversaryEntityRepository anniversaryEntityRepository,
        NoticeEntityRepository noticeEntityRepository,
        AnniversaryMapper anniversaryMapper,
        NoticeMapper noticeMapper
    ) {
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
            .map(it -> noticeMapper.toEntity(it, anniversaryId))
            .toList();

        noticeEntityRepository.saveAll(noticeEntities);
        return anniversaryId;
    }

    @Override
    public void saveAll(List<Anniversary> anniversaryList) {
        List<AnniversaryEntity> anniversaryEntities = anniversaryList.stream()
            .map(it -> anniversaryMapper.toEntity(it))
            .toList();
        anniversaryEntityRepository.saveAll(anniversaryEntities);
    }

    @Override
    public List<Anniversary> findAll() {
        final List<AnniversaryEntity> anniversaryList = anniversaryEntityRepository.findAll();

        return anniversaryList
            .stream()
            .map(it -> anniversaryMapper.toDomain(it, getNotice(it.getId())))
            .toList();
    }

    @Override
    public Anniversary findById(final Long anniversaryId) {
        final AnniversaryEntity anniversaryEntity = anniversaryEntityRepository.findById(
                anniversaryId)
            .orElseThrow(() -> new NotFoundAnniversaryException(anniversaryId));

        final List<Notice> notices = getNotice(anniversaryId);

        return anniversaryMapper.toDomain(anniversaryEntity, notices);
    }

    private List<Notice> getNotice(Long anniversaryId) {
        return noticeEntityRepository.findAllByAnniversaryId(anniversaryId)
            .stream()
            .map(noticeMapper::toDomain)
            .toList();
    }

    @Override
    public List<Anniversary> findByDeviceUuidOrderByRecentDate(final String deviceId) {
        return anniversaryEntityRepository.findByDeviceUuidOrderByRecentDate(deviceId)
            .stream()
            .map(anniversaryMapper::toDomain)
            .toList();
    }

    @Override
    public void update(final Anniversary anniversary) {
        removeBeforeNotice(anniversary);

        final AnniversaryEntity anniversaryEntity = anniversaryMapper.toEntity(anniversary);
        anniversaryEntityRepository.save(anniversaryEntity);
        final List<NoticeEntity> noticeEntities = anniversary.getNotices()
            .stream()
            .map(it -> noticeMapper.toEntity(it, anniversaryEntity.getId()))
            .toList();
        noticeEntityRepository.saveAll(noticeEntities);
    }

    private void removeBeforeNotice(final Anniversary anniversary) {
        noticeEntityRepository.deleteByAnniversaryId(anniversary.getId());
        noticeEntityRepository.flush();
    }

    @Override
    public void delete(final Anniversary anniversary) {
        final AnniversaryEntity anniversaryEntity = anniversaryMapper.toEntity(anniversary);
        anniversaryEntityRepository.delete(anniversaryEntity);

        final List<Long> deleteIds = anniversary.getNotices()
            .stream()
            .map(Notice::getId)
            .toList();
        noticeEntityRepository.deleteAllByIdInBatch(deleteIds);
    }
}
