package com.dontforget.dontforget.domain.anniversary;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import autoparams.AutoSource;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

@DisplayName("Notice 도메인 테스트")
class NoticeTest {

    @ParameterizedTest
    @AutoSource
    void Notice_생성_테스트(
        final Long id,
        final Long anniversaryId,
        final NoticeType type
    ) {
        // given
        // when
        Notice notice = new Notice(id, anniversaryId, type);

        // then
        assertAll(
            () -> assertThat(notice.getId()).isEqualTo(id),
            () -> assertThat(notice.getAnniversaryId()).isEqualTo(anniversaryId),
            () -> assertThat(notice.getNoticeType()).isEqualTo(type)
        );
    }
}
