package com.dontforget.dontforget.infra.jpa.notice;

import com.dontforget.dontforget.domain.notice.DeviceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NoticeDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_uuid", nullable = false)
    private String deviceUuid;

    @Column(name = "device_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;
}
