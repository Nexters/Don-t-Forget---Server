package com.dontforget.dontforget.infra.anniversary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

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
  private LocalDateTime lunarDate;

  @Column(name = "solar_date", nullable = false)
  private LocalDateTime solarDate;

  @Builder
  public AnniversaryEntity(
      String title,
      String content,
      String deviceUuid,
      LocalDateTime lunarDate,
      LocalDateTime solarDate) {
    this(null, title, content, deviceUuid, lunarDate, solarDate);
  }
}
