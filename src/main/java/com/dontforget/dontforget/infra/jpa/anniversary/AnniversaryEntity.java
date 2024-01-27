package com.dontforget.dontforget.infra.jpa.anniversary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "anniversary")
public class AnniversaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String content;

    @Column(name = "device_uuid", nullable = false)
    private String deviceUuid;

    @Column(name = "lunar_date", nullable = false)
    private LocalDate lunarDate;

    @Column(name = "solar_date", nullable = false)
    private LocalDate solarDate;

    public AnniversaryEntity(
        Long id, String title, String content,
        String deviceUuid, LocalDate lunarDate, LocalDate solarDate
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.deviceUuid = deviceUuid;
        this.lunarDate = lunarDate;
        this.solarDate = solarDate;
    }

    public AnniversaryEntity(
        String title, String content, String deviceUuid,
        LocalDate lunarDate, LocalDate solarDate
    ) {
        this(null, title, content, deviceUuid, lunarDate, solarDate);
    }
}
