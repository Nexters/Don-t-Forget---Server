package com.dontforget.dontforget.domain.notice;

public interface NoticeDeviceRepository {
    NoticeDevice findByUuid(final String uuid);

    Long save(final NoticeDevice noticeDevice);
}
