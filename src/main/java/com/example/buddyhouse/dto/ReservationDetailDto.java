package com.example.buddyhouse.dto;

import com.example.buddyhouse.entity.CustomerEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.LongSummaryStatistics;
import lombok.Builder;
import lombok.Getter;

/**
 * 予約の詳細情報
 */
@Getter
@Builder
public class ReservationDetailDto {

  private Long id;

  private long customerId;

  private String customerName;

  private String customerPhone;

  private String menuName;

  private List<Long> petsId;

  private List<String> petsName;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
