package com.dontforget.dontforget.infra.anniversary;

import com.dontforget.dontforget.domain.anniversary.Anniversary;
import com.dontforget.dontforget.domain.notice.Notice;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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


    public Anniversary toDomain(final List<Notice> notices) {
        return new Anniversary(
                this.id,
                this.title,
                this.content,
                this.deviceUuid,
                this.lunarDate,
                this.solarDate,
                notices
        );
    }

    public static AnniversaryEntity from(final Anniversary anniversary) {
        return new AnniversaryEntity(
                anniversary.getId(),
                anniversary.getTitle(),
                anniversary.getContent(),
                anniversary.getDeviceUuid(),
                anniversary.getLunarDate(),
                anniversary.getSolarDate()
        );
    }
}
