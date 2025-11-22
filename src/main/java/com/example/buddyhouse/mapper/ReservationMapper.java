package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.entity.ReservationEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
  public ReservationDto toDto(ReservationEntity entity){

    List<Long> petsId = entity.getReservationPets().stream()
        .map(reservationPetEntity -> reservationPetEntity.getPets().getId())
        .toList();

    return ReservationDto.builder()
        .id(entity.getId())
        .reservationFrameId(entity.getFrame().getId())
        .customerId(entity.getCustomer().getId())
        .menuId(entity.getMenu().getId())
        .startAt(entity.getStartAt())
        .endAt(entity.getEndAt())
        .petsId(petsId)
        .build();

  }


}
