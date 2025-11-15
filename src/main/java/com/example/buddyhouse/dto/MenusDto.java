package com.example.buddyhouse.dto;

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
  private boolean overnight;
  private boolean deleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;


}
