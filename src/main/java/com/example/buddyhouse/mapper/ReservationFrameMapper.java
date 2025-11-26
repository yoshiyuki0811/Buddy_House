package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.dto.ReservationFrameListDto;
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
    dto.setClose(entity.isClosed());
    dto.setDeleted(entity.isDeleted());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());

    return dto;
  }

  public ReservationFrameListDto toListDto(ReservationFrameEntity entity){

    ReservationFrameListDto dto = new ReservationFrameListDto();
    dto.setId(entity.getId());
    dto.setReservationType(entity.getReservationType());
    dto.setStartAt(entity.getStartAt());
    dto.setEndAt(entity.getEndAt());
    dto.setMaxDogs(entity.getMaxDogs());
    dto.setUsedDogs(entity.getUsedDogs());
    return dto;
  }
  // DtoからEntityに変換
  public ReservationFrameEntity toEntity(ReservationFrameDto dto){
    return ReservationFrameEntity
        .builder()
        .reservationType(dto.getReservationType())
        .startAt(dto.getStartAt())
        .endAt(dto.getEndAt())
        .maxDogs(dto.getMaxDogs())
        .build();

  }
}
