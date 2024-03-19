package com.dontforget.dontforget.domain;

import static org.assertj.core.api.Assertions.assertThat;

import autoparams.AutoSource;
import com.dontforget.dontforget.common.CardType;
import com.dontforget.dontforget.config.RepositoryTestConfig;
import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.anniversary.AnniversaryRepository;
import com.dontforget.dontforget.domain.notice.Notice;
import com.dontforget.dontforget.domain.notice.NoticeRepository;
import com.dontforget.dontforget.domain.notice.NoticeTarget;
import com.dontforget.dontforget.domain.notice.service.AlarmSearcher;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
class AlarmSearcherTest {

    @Autowired
    private AnniversaryRepository anniversaryRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    @ParameterizedTest
    @AutoSource
    void 양력을_기준으로_디데이와_몇일전의_알림_발송할_대상을_찾는다(
        final LocalDate today,
        final List<Notice> notices
    ) {
        var anniversary = new Anniversary(
            null,
            "title",
            "content",
            "deviceUuid",
            LocalDate.now(),
            "SOLAR",
            LocalDate.now(),
            today,
            notices,
            CardType.ARM
        );
        Long anniversaryId = anniversaryRepository.save(anniversary);
        setProperties(anniversary, "id", anniversaryId);
        // given
        var sut = new AlarmSearcher(anniversaryRepository);

        // when
        var actual = sut.findAlarmTargets(today);

        // then
        var findNotices = noticeRepository.findAllByAnniversaryId(anniversaryId);
        var findAnniversaries = anniversaryRepository.findById(anniversaryId);
        var expected = findNotices
            .stream()
            .map(it-> NoticeTarget.of(findAnniversaries, it))
            .toList();

        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }



    private void setProperties(
        Object object,
        String fieldName,
        Object value
    ) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            //ignore
        }
    }
}
