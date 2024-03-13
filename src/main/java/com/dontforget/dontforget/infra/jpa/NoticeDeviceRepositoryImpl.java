package com.dontforget.dontforget.infra.jpa;

import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRepository;
import com.dontforget.dontforget.domain.notice.exception.NotFoundNoticeDeviceException;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeDeviceEntityRepository;
import com.dontforget.dontforget.infra.mapper.NoticeDeviceMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDeviceRepositoryImpl implements NoticeDeviceRepository {

    private final NoticeDeviceEntityRepository noticeDeviceRepository;
    private final NoticeDeviceMapper noticeDeviceMapper;

    public NoticeDeviceRepositoryImpl(
        final NoticeDeviceEntityRepository noticeDeviceRepository,
        final NoticeDeviceMapper noticeDeviceMapper
    ) {
        this.noticeDeviceRepository = noticeDeviceRepository;
        this.noticeDeviceMapper = noticeDeviceMapper;
    }

    @Override
    public NoticeDevice findByUuid(final String uuid) {
        var deviceEntity = noticeDeviceRepository
            .findNoticeDeviceEntityByDeviceUuid(uuid)
            .orElseThrow(() -> new NotFoundNoticeDeviceException(uuid));

        return noticeDeviceMapper.toDomain(deviceEntity);
    }

    @Override
    public Long upsert(final NoticeDevice noticeDevice) {
        var existingDevice = noticeDeviceRepository
            .findNoticeDeviceEntityByDeviceUuid(noticeDevice.getDeviceUuid())
            .orElse(null);

        var noticeDeviceEntity = existingDevice != null ?
            noticeDeviceMapper.toEntity(noticeDevice, existingDevice.getId()) :
            noticeDeviceMapper.toEntity(noticeDevice);

        return noticeDeviceRepository.save(noticeDeviceEntity).getId();
    }
}
