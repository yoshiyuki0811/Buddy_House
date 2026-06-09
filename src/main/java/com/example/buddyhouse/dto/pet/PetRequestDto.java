package com.example.buddyhouse.dto.pet;

import com.example.buddyhouse.enums.WeightCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
  @NotBlank
  @Size(max = 50)
  private String name;

  @NotBlank
  @Size(max = 100)
  private String breed;

  @NotNull
  private WeightCategory weight;

  @Min(0)
  private Integer age;

  @Size(max = 1000)
  private String feature;
}
