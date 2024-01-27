package com.dontforget.dontforget.domain.anniversary;

import autoparams.AutoSource;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Notice 도메인 테스트")
class NoticeTest {
    @ParameterizedTest
    @AutoSource
    @DisplayName("Notice 생성 테스트")
    void sut_create_notice_success(
            final Long id,
            final Long anniversaryId,
            final NoticeType type
    ) {
        // given
        // when
        Notice notice = new Notice(id, anniversaryId, type);

        // then
        assertThat(notice.getId()).isEqualTo(id);
        assertThat(notice.getAnniversaryId()).isEqualTo(anniversaryId);
        assertThat(notice.getNoticeType()).isEqualTo(type);
    }
}
