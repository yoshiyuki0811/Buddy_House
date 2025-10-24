package com.example.buddyhouse.dto;

import com.example.buddyhouse.entity.CustomerEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
  private Long id;
  private String name;
  private String address;
  private String email;
  private String phone;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Entity → DTO に変換するコンストラクタ（これが重要！）
  public CustomerDto(CustomerEntity entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.createdAt = entity.getCreatedAt();
    this.updatedAt = entity.getUpdatedAt();
  }



}
