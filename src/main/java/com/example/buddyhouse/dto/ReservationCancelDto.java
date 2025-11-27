package com.example.buddyhouse.dto;

import com.example.buddyhouse.enums.ReservationStatus;
import com.example.buddyhouse.enums.ReservationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCancelDto {
  private Long reservationId;
  private ReservationStatus status;
}
