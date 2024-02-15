package com.dontforget.dontforget.infra.jpa;

import com.dontforget.dontforget.domain.notice.NoticeDevice;
import com.dontforget.dontforget.domain.notice.NoticeDeviceRepository;
import com.dontforget.dontforget.domain.notice.exception.NotFoundNoticeDeviceException;
import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeDeviceEntityRepository;
import com.dontforget.dontforget.infra.mapper.NoticeMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDeviceRepositoryImpl implements NoticeDeviceRepository {

    private final NoticeDeviceEntityRepository noticeDeviceRepository;
    private final NoticeMapper noticeMapper;

    public NoticeDeviceRepositoryImpl(NoticeDeviceEntityRepository noticeDeviceRepository,
        NoticeMapper noticeMapper) {
        this.noticeDeviceRepository = noticeDeviceRepository;
        this.noticeMapper = noticeMapper;
    }

    @Override
    public NoticeDevice findByUuid(String uuid) {
        NoticeDeviceEntity findDevice = noticeDeviceRepository
            .findNoticeDeviceEntityByDeviceUuid(uuid)
            .orElseThrow(() -> new NotFoundNoticeDeviceException(uuid));

        return noticeMapper.toDomain(findDevice);
    }

    @Override
    public Long save(NoticeDevice noticeDevice) {
        return noticeDeviceRepository
            .save(noticeMapper.toEntity(noticeDevice))
            .getId();
    }

    @Override
    public Long upsert(NoticeDevice noticeDevice) {
        NoticeDeviceEntity existingDevice = noticeDeviceRepository
            .findNoticeDeviceEntityByDeviceUuid(noticeDevice.getDeviceUuid())
            .orElse(null);

        existingDevice = existingDevice != null ?
            noticeMapper.toEntity(noticeDevice, existingDevice.getId()) :
            noticeMapper.toEntity(noticeDevice);

        return noticeDeviceRepository.save(existingDevice).getId();
    }
}
