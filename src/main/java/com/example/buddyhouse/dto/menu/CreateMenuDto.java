package com.example.buddyhouse.dto.menu;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMenuDto {
  private String name;
  private String feature;

}
