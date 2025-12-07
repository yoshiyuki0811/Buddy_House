package com.example.buddyhouse.dto.reservation;

import com.example.buddyhouse.enums.ReservationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCancelDto {
  private Long reservationId;
  private ReservationType reservationType;
}
