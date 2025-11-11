package com.example.buddyhouse.dto;

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
}
