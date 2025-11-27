package com.example.buddyhouse.dto;

import com.example.buddyhouse.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationDto {
  private Long id;

  private String customerName;

  private String  menuName;

  private List<String> petsName;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  private ReservationStatus status;


}
