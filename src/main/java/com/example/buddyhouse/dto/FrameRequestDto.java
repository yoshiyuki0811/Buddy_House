package com.example.buddyhouse.dto;

import com.example.buddyhouse.enums.ReservationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FrameRequestDto {

  private ReservationType reservationType;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  private Integer maxDogs;

}
