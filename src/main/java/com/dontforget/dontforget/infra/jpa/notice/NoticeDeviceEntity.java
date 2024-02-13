package com.dontforget.dontforget.infra.jpa.notice;

import com.dontforget.dontforget.domain.notice.DeviceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notice_device")
public class NoticeDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_uuid", nullable = false)
    private String deviceUuid;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;

    @Column(name = "device_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;

    public NoticeDeviceEntity(Long id, String deviceUuid, String fcmToken,
        DeviceStatus deviceStatus) {
        this.id = id;
        this.deviceUuid = deviceUuid;
        this.fcmToken = fcmToken;
        this.deviceStatus = deviceStatus;
    }
}
