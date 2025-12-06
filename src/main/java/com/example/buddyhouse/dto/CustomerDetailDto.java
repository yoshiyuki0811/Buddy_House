package com.example.buddyhouse.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class CustomerDetailDto {

  private Long id;
  private String name;
  private String address;
  private String email;
  private String phone;

}
