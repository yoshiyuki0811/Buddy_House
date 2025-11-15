package com.example.buddyhouse.dto;

import com.example.buddyhouse.enums.ReservationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFrameDto {
  private Long id;
  private ReservationType reservationType;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Integer maxDogs;
  private Integer usedDogs;
  private boolean open;
  private boolean deleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
