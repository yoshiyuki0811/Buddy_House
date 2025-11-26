package com.example.buddyhouse.dto;

import com.example.buddyhouse.enums.ReservationType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenusDto {
  private Long id;
  private String name;
  private String feature;
  private Boolean active;
  private ReservationType reservationType;
  private boolean deleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;


}
