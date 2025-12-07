package com.example.buddyhouse.dto.pet;

import com.example.buddyhouse.enums.WeightCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetsListDto {
  private Long id; //ペットID
  private String name; //ペット名
  private String breed ; //犬種
  private WeightCategory weight; //　体重区分（Toy,Small,Medium,Large,Giant）

}
