package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import org.springframework.stereotype.Component;
@Component
public class ReservationFrameMapper {

  /** Entity → DTO */
  public ReservationFrameDto toDto(ReservationFrameEntity entity) {

    ReservationFrameDto dto = new ReservationFrameDto();

    dto.setId(entity.getId());
    dto.setReservationType(entity.getReservationType());
    dto.setStartAt(entity.getStartAt());
    dto.setEndAt(entity.getEndAt());
    dto.setMaxDogs(entity.getMaxDogs());
    dto.setUsedDogs(entity.getUsedDogs());
    dto.setOpen(entity.isOpen());
    dto.setDeleted(entity.isDeleted());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());

    return dto;
  }
  public ReservationFrameEntity toEntity(ReservationFrameDto dto){
    ReservationFrameEntity entity = new ReservationFrameEntity();
        entity.setId(dto.getId());
        entity.setReservationType(dto.getReservationType());
        entity.setStartAt(dto.getStartAt());
        entity.setEndAt(dto.getEndAt());
        entity.setMaxDogs(dto.getMaxDogs());
        entity.setUsedDogs(dto.getUsedDogs());
        entity.setOpen(dto.isOpen());
        entity.setDeleted(dto.isDeleted());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;

  }
}
