package com.example.buddyhouse.dto.resrvationFrame;

import com.example.buddyhouse.enums.ReservationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FrameRequestDto {

  private ReservationType reservationType;
  @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Asia/Tokyo")
  private LocalDateTime startAt;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Asia/Tokyo")
  private LocalDateTime endAt;

  private Integer maxDogs;

}
