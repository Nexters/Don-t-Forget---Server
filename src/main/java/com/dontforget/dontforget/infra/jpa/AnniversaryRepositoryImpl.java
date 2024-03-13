package com.dontforget.dontforget.infra.jpa;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.anniversary.exception.NotFoundAnniversaryException;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.infra.jpa.anniversary.AnniversaryEntity;
import com.dontforget.dontforget.infra.jpa.anniversary.repository.AnniversaryEntityRepository;
import com.dontforget.dontforget.infra.mapper.AnniversaryMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AnniversaryRepositoryImpl implements AnniversaryRepository {

    private final AnniversaryEntityRepository anniversaryEntityRepository;
    private final AnniversaryMapper anniversaryMapper;
    private final NoticeRepository noticeRepository;

    public AnniversaryRepositoryImpl(
        final AnniversaryEntityRepository anniversaryEntityRepository,
        final AnniversaryMapper anniversaryMapper,
        final NoticeRepository noticeRepository
    ) {
        this.anniversaryEntityRepository = anniversaryEntityRepository;
        this.anniversaryMapper = anniversaryMapper;
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Long save(final Anniversary anniversary) {
        final AnniversaryEntity anniversaryEntity = anniversaryMapper.toEntity(anniversary);
        final Long anniversaryId = anniversaryEntityRepository.save(anniversaryEntity)
            .getId();

        noticeRepository.saveAll(anniversaryId, anniversary.getNotices());
        return anniversaryId;
    }

    @Override
    public void saveAll(final List<Anniversary> anniversaryList) {
        var anniversaryEntities = anniversaryList.stream()
            .map(anniversaryMapper::toEntity)
            .toList();
        anniversaryEntityRepository.saveAll(anniversaryEntities);
    }

    @Override
    public List<Anniversary> findAll() {
        final List<AnniversaryEntity> anniversaryList = anniversaryEntityRepository.findAll();

        return anniversaryList
            .stream()
            .map(it -> {
                var notices = noticeRepository.findAllByAnniversaryId(it.getId());
                return anniversaryMapper.toDomain(it, notices);
            }).toList();
    }

    @Override
    public Anniversary findById(final Long anniversaryId) {
        final AnniversaryEntity anniversaryEntity = anniversaryEntityRepository.findById(
                anniversaryId)
            .orElseThrow(() -> new NotFoundAnniversaryException(anniversaryId));

        final List<Notice> notices = noticeRepository.findAllByAnniversaryId(anniversaryId);
        return anniversaryMapper.toDomain(anniversaryEntity, notices);
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
        noticeRepository.deleteNoticeEntites(anniversary.getId());

        final AnniversaryEntity anniversaryEntity = anniversaryMapper.toEntity(anniversary);
        anniversaryEntityRepository.save(anniversaryEntity);

        noticeRepository.saveAll(anniversaryEntity.getId(), anniversary.getNotices());
    }

    @Override
    public void delete(final Anniversary anniversary) {
        final AnniversaryEntity anniversaryEntity = anniversaryMapper.toEntity(anniversary);
        anniversaryEntityRepository.delete(anniversaryEntity);
        noticeRepository.deleteNoticeEntites(anniversary.getId());
    }
}
