package com.example.buddyhouse.dto.menu;

import com.example.buddyhouse.enums.ReservationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenusListDto {
  private Long id;
  private String name;
  private String feature;
  private Boolean active;
  private ReservationType reservationType;
}
