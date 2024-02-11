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

    @Column(name = "base_date", nullable = true)
    private LocalDate baseDate;

    @Column(name = "base_type", nullable = true)
    private String baseType;

    @Column(name = "lunar_date", nullable = false)
    private LocalDate lunarDate;

    @Column(name = "solar_date", nullable = false)
    private LocalDate solarDate;

    @Column(name = "card_type", nullable = false)
    private String cardType;

    public AnniversaryEntity(Long id, String title, String content, String deviceUuid,
        LocalDate baseDate, String baseType, LocalDate lunarDate, LocalDate solarDate,
        String cardType) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.deviceUuid = deviceUuid;
        this.baseDate = baseDate;
        this.baseType = baseType;
        this.lunarDate = lunarDate;
        this.solarDate = solarDate;
        this.cardType = cardType;
    }

    public AnniversaryEntity(
        String title,
        String content,
        String deviceUuid,
        LocalDate baseDate,
        String baseType,
        LocalDate lunarDate,
        LocalDate solarDate,
        String cardType
    ) {
        this(null, title, content, deviceUuid, baseDate, baseType, lunarDate, solarDate, cardType);
    }
}
