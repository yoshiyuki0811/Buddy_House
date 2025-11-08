package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.MenusDto;
import com.example.buddyhouse.entity.MenusEntity;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MenusMapper {

  // Entity → DTO に変換するコンストラクタ
  public MenusDto toDto(MenusEntity entity) {
    MenusDto dto = new MenusDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setFeature(entity.getFeature());
    dto.setActive(entity.isActive());
    dto.setDeleted(entity.isDeleted());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    return dto;
  }
  //DTOからEntityへ変換
  public MenusEntity toEntity(MenusDto dto) {
    MenusEntity entity = new MenusEntity();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    if (dto.getActive() != null)  entity.setActive(dto.getActive());   // ← null のときは上書きしない
    entity.setFeature(dto.getFeature());
    entity.setDeleted(dto.isDeleted());
    entity.setCreatedAt(LocalDateTime.now());
    entity.setUpdatedAt(LocalDateTime.now());
    return entity;
  }
}
