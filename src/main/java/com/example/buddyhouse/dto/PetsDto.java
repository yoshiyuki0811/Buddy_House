package com.example.buddyhouse.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDateTime;
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
  private String weight; //　体重区分（Toy,Small,Medium,Large,Giant）
  private Integer age; //年齢
  private String feature; //特徴や性格メモ
  private boolean deleted; //論理削除フラグ
  private LocalDateTime createdAt; //登録日時
  private LocalDateTime updatedAt; //更新日時
}
