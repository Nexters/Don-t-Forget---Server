package com.dontforget.dontforget.infra.jpa;

import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.domain.notice.enums.NoticeStatus;
import com.dontforget.dontforget.infra.jpa.notice.NoticeEntity;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeEntityRepository;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import java.util.List;

public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeEntityRepository noticeEntityRepository;
    private final NoticeMapper noticeMapper;

    public NoticeRepositoryImpl(
        NoticeEntityRepository noticeEntityRepository,
        NoticeMapper noticeMapper
    ) {
        this.noticeEntityRepository = noticeEntityRepository;
        this.noticeMapper = noticeMapper;
    }

    @Override
    public List<Notice> findAllByAnniversaryId(final Long anniversaryId) {
        var noticeEntities = noticeEntityRepository.findAllByAnniversaryId(anniversaryId);

        return noticeEntities
            .stream()
            .map(noticeMapper::toDomain)
            .toList();
    }

    @Override
    public List<NoticeEntity> saveAll(final Long anniversaryId, final List<Notice> notices) {
        var noticeEntities = notices.stream()
            .map(it -> noticeMapper.toEntity(it, anniversaryId))
            .toList();
        return noticeEntityRepository.saveAll(noticeEntities);
    }

    @Override
    public void deleteNoticeEntites(final Long anniversaryId) {
        noticeEntityRepository.deleteByAnniversaryId(anniversaryId);
        noticeEntityRepository.flush();
    }

    @Override
    public void updateNoticeStatus(NoticeStatus noticeStatus, List<Long> noticeIds) {
        noticeEntityRepository.updateNoticeStatus(noticeStatus, noticeIds);
    }
}
