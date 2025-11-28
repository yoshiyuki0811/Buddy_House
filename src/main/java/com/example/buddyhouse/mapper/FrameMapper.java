package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.FrameDto;
import com.example.buddyhouse.dto.FrameListDto;
import com.example.buddyhouse.dto.FrameRequestDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import org.springframework.stereotype.Component;
@Component
public class  FrameMapper {

  /** Entity → DTO */
  public FrameDto toDto(ReservationFrameEntity entity) {

    FrameDto dto = new FrameDto();

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

  public FrameListDto toListDto(ReservationFrameEntity entity){

    FrameListDto dto = new FrameListDto();
    dto.setId(entity.getId());
    dto.setReservationType(entity.getReservationType());
    dto.setStartAt(entity.getStartAt());
    dto.setEndAt(entity.getEndAt());
    dto.setMaxDogs(entity.getMaxDogs());
    dto.setUsedDogs(entity.getUsedDogs());
    return dto;
  }
  // DtoからEntityに変換
  public ReservationFrameEntity toEntity(FrameRequestDto requestDto){
    return ReservationFrameEntity
        .builder()
        .reservationType(requestDto.getReservationType())
        .startAt(requestDto.getStartAt())
        .endAt(requestDto.getEndAt())
        .maxDogs(requestDto.getMaxDogs())
        .build();

  }

}
