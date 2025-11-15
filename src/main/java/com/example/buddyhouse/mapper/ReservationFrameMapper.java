package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import lombok.Builder;
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
    return ReservationFrameEntity.builder()
        .id(dto.getId())
        .reservationType(dto.getReservationType())
        .startAt(dto.getStartAt())
        .endAt(dto.getEndAt())
        .maxDogs(dto.getMaxDogs())
        .usedDogs(dto.getUsedDogs())
        .open(dto.isOpen())
        .deleted(dto.isDeleted())
        .createdAt(dto.getCreatedAt())
        .updatedAt(dto.getUpdatedAt())
        .build();
  }
}
