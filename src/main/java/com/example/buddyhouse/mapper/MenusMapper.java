package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.menu.CreateMenuDto;
import com.example.buddyhouse.dto.menu.MenusListDto;
import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.enums.ReservationType;
import org.springframework.stereotype.Component;

@Component
public class MenusMapper {

  //DTOからEntityへ変換
  public MenuEntity toCreateEntity(ReservationType type,CreateMenuDto dto) {
    MenuEntity entity = new MenuEntity();
    entity.setName(dto.getName());
    entity.setFeature(dto.getFeature());
    entity.setReservationType(type);
    return entity;
  }
  // Entity → DTO に変換するコンストラクタ
  public MenusListDto toListDto(MenuEntity entity) {
    MenusListDto dto = new MenusListDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setFeature(entity.getFeature());
    dto.setActive(entity.isActive());
    dto.setReservationType(entity.getReservationType());
    return dto;
  }

}
