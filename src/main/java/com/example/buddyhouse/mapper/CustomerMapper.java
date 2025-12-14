package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.customer.CustomerDetailDto;
import com.example.buddyhouse.dto.customer.CustomerDto;
import com.example.buddyhouse.dto.customer.CustomerRequestDto;
import com.example.buddyhouse.dto.customer.CustomersListDto;
import com.example.buddyhouse.dto.auth.SignupRequestDto;
import com.example.buddyhouse.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

  // Entity → DTO に変換するコンストラクタ
  public CustomerDto toDto(CustomerEntity entity) {
    CustomerDto dto = new CustomerDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setEmail(entity.getEmail());
    dto.setPhone(entity.getPhone());
    dto.setAddress(entity.getAddress());
    dto.setDeleted(entity.isDeleted());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    return dto;
  }
    //DTOからEntityへ変換
    public CustomerEntity toEntity(CustomerRequestDto dto) {
    return  CustomerEntity.builder()
          .name(dto.getName())
          .email(dto.getEmail())
          .phone(dto.getPhone())
          .address(dto.getAddress())
          .build();
          
      
    }
  // Entity → DTO に変換する(顧客一覧取得用)
  public CustomersListDto toListDto(CustomerEntity entity) {
    CustomersListDto dto = new CustomersListDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setPhone(entity.getPhone());
    return dto;
  }

  public CustomerDetailDto DetailDto(CustomerEntity entity){
    return CustomerDetailDto.builder()
        .id(entity.getId())
        .email(entity.getEmail())
        .name(entity.getName())
        .phone(entity.getPhone())
        .address(entity.getAddress())
        .build();
  }


  public CustomerEntity toEntity(SignupRequestDto dto) {

    return CustomerEntity.builder()
        .name(dto.getName())
        .phone(dto.getPhone())
        .address(dto.getAddress())
        .email(dto.getEmail())
        .build();
  }
  public CustomerRequestDto toRequestDto(CustomerEntity entity){
    return CustomerRequestDto.builder()
        .name(entity.getName())
        .address(entity.getAddress())
        .email(entity.getEmail())
        .phone(entity.getPhone())
        .build();
  }


}


