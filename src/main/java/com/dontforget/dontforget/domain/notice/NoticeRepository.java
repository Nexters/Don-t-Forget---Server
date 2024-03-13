package com.dontforget.dontforget.domain.notice;

import java.util.List;

public interface NoticeRepository {

    List<Notice>  findAllByAnniversaryId(final Long anniversaryId);

    void saveAll(final Long anniversaryId, List<Notice> notices);

    void deleteNoticeEntites(Long anniversaryId);
}
