package com.example.buddyhouse.dto.pet;


import com.example.buddyhouse.enums.WeightCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PetsDto {
  private Long id; //ペットID
  private Long customerId;
  private String name; //ペット名
  private String breed ; //犬種
  private WeightCategory weight; //　体重区分（Toy,Small,Medium,Large,Giant）
  private Integer age; //年齢
  private String feature; //特徴や性格メモ
}
