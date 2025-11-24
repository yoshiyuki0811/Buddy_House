package com.example.buddyhouse.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class ReservationDto {
  private Long id;

  private Long reservationFrameId;

  private Long customerId;

  private Long menuId;

  private List<Long> petsId;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  private boolean deleted;

}
