package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.dto.CustomersListDto;
import com.example.buddyhouse.entity.CustomerEntity;
import java.time.LocalDateTime;
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
    public CustomerEntity toEntity(CustomerDto dto) {
      CustomerEntity entity = new CustomerEntity();
      entity.setId(dto.getId());
      entity.setName(dto.getName());
      entity.setEmail(dto.getEmail());
      entity.setPhone(dto.getPhone());
      entity.setAddress(dto.getAddress());
      entity.setDeleted(dto.isDeleted());
      entity.setCreatedAt(LocalDateTime.now());
      entity.setUpdatedAt(LocalDateTime.now());
      return entity;
    }
  // Entity → DTO に変換する(顧客一覧取得用)
  public CustomersListDto toListDto(CustomerEntity entity) {
    CustomersListDto dto = new CustomersListDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setPhone(entity.getPhone());
    return dto;
  }
  //DTOからEntityへ変換(顧客一覧取得用)
  public CustomerEntity toListEntity(CustomersListDto dto) {
    CustomerEntity entity = new CustomerEntity();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setPhone(dto.getPhone());
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());
    return entity;
  }
  }


