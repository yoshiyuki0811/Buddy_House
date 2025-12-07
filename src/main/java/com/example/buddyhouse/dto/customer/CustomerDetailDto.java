package com.example.buddyhouse.dto.customer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDetailDto {

  private Long id;
  private String name;
  private String address;
  private String email;
  private String phone;

}
