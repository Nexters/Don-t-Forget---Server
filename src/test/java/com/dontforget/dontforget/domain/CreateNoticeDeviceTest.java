package com.dontforget.dontforget.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.dontforget.dontforget.domain.notice.enums.DeviceStatus;
import com.dontforget.dontforget.domain.notice.query.NoticeDeviceRequest;
import com.dontforget.dontforget.domain.notice.service.CreateNoticeDevice;
import com.dontforget.dontforget.infra.jpa.NoticeDeviceRepositoryImpl;
import com.dontforget.dontforget.infra.jpa.notice.NoticeDeviceEntity;
import com.dontforget.dontforget.infra.jpa.notice.repository.NoticeDeviceEntityRepository;
import com.dontforget.dontforget.infra.mapper.NoticeDeviceMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("Service - Create Notice Device")
@DataJpaTest
class CreateNoticeDeviceTest {

    @DisplayName("device entity가 없으면, 생성된다.")
    @Test
    void test() {
        // given
        var request = new NoticeDeviceRequest("token", "deviceUuid", DeviceStatus.ON);
        var noticeDeviceEntityRepository = Mockito.mock(NoticeDeviceEntityRepository.class);
        var noticeDeviceStub = new NoticeDeviceRepositoryImpl(noticeDeviceEntityRepository,
            new NoticeDeviceMapper());
        when(noticeDeviceEntityRepository
            .findNoticeDeviceEntityByDeviceUuid("deviceUuid"))
            .thenReturn(Optional.empty());
        when(noticeDeviceEntityRepository.save(Mockito.any()))
            .thenReturn(new NoticeDeviceEntity(1L, "token", "deviceUuid", DeviceStatus.ON));

        var sut = new CreateNoticeDevice(noticeDeviceStub);

        // when
        Long id = sut.upsert(request);

        // then
        Mockito.verify(noticeDeviceEntityRepository).save(Mockito.any());
    }

    @Autowired
    private NoticeDeviceEntityRepository noticeDeviceEntityRepository;

    @DisplayName("device entity가 있으면, 업데이트된다.")
    @Test
    void test2() {
        // given
        noticeDeviceEntityRepository.save(
            new NoticeDeviceEntity(1L, "deviceUuid", "token", DeviceStatus.ON));
        var request = new NoticeDeviceRequest("token", "deviceUuid", DeviceStatus.OFF);
        var noticeDeviceStub = new NoticeDeviceRepositoryImpl(noticeDeviceEntityRepository,
            new NoticeDeviceMapper());
        var sut = new CreateNoticeDevice(noticeDeviceStub);

        // when
        sut.upsert(request);
        var device = noticeDeviceStub.findByUuid("deviceUuid");

        // then
        assertThat(device.getDeviceStatus()).isEqualTo(DeviceStatus.OFF);
    }
}
