package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.ReservationCancelDto;
import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.dto.ReservationFrameListDto;
import com.example.buddyhouse.dto.ReservationListDto;
import com.example.buddyhouse.entity.ReservationEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

  /**
   *
   * @param entity
   * @return
   */
 private List<String> extractPetName(ReservationEntity entity){
  return entity.getReservationPets().stream()
       .map(reservationPetEntity -> reservationPetEntity
           .getPets()
           .getName())
       .toList();
 }


  public ReservationDto toDto(ReservationEntity entity){


    return ReservationDto.builder()
        .id(entity.getId())
        .customerName(entity.getCustomer().getName())
        .menuName(entity.getMenu().getName())
        .startAt(entity.getStartAt())
        .endAt(entity.getEndAt())
        .petsName(extractPetName(entity))
        .build();

  }
public ReservationCancelDto toCancelDto(ReservationEntity entity){
    return ReservationCancelDto.builder()
        .reservationId(entity.getId())
        .reservationType(entity.getFrame().getReservationType())
        .build();
}
public ReservationListDto toListDto(ReservationEntity entity) {
  return ReservationListDto.builder()
      .id(entity.getId())
      .customerName(entity.getCustomer().getName())
      .petsName(extractPetName(entity))
      .menuName(entity.getMenu().getName())
      .startAt(entity.getStartAt())
      .endAt(entity.getEndAt())
      .build();
}
}
