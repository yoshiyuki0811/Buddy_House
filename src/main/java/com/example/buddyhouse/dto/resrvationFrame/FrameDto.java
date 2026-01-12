package com.example.buddyhouse.dto.resrvationFrame;

import com.example.buddyhouse.enums.ReservationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrameDto {
  private Long id;
  private ReservationType reservationType;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Asia/Tokyo")
  private LocalDateTime startAt;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm",timezone = "Asia/Tokyo")
  private LocalDateTime endAt;

  private Integer maxDogs;
  private Integer usedDogs;
  private boolean close;
  private boolean deleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
