package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.entity.CustomerEntity;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component

public class CustomerMapper {

  // Entity → DTO に変換するコンストラクタ
  public CustomerDto createDto(CustomerEntity entity) {
    CustomerDto dto = new CustomerDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setEmail(entity.getEmail());
    dto.setPhone(entity.getPhone());
    dto.setAddress(entity.getAddress());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    return dto;
  }
    //DTOからEntityへ変換
    public CustomerEntity createEntity(CustomerDto dto) {
      CustomerEntity entity = new CustomerEntity();
      entity.setName(dto.getName());
      entity.setEmail(dto.getEmail());
      entity.setPhone(dto.getPhone());
      entity.setAddress(dto.getAddress());
      entity.setCreatedAt(LocalDateTime.now());
      entity.setUpdatedAt(LocalDateTime.now());
      return entity;
    }
  }


