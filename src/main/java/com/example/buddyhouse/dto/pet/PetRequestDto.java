package com.example.buddyhouse.dto.pet;

import com.example.buddyhouse.enums.WeightCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetRequestDto {
  private String name;
  private String breed;
  private WeightCategory weight;
  private Integer age;
  private String feature;

}
