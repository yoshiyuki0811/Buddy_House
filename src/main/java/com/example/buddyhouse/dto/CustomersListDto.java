package com.example.buddyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//顧客情報一覧表示に使う用のDTO
public class CustomersListDto {
  private Long id;
  private String name;
  private String phone;
}
