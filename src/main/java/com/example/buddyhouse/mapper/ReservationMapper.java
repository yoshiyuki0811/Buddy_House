package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.ReservationCancelDto;
import com.example.buddyhouse.dto.ReservationDetailDto;
import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.dto.ReservationListDto;
import com.example.buddyhouse.entity.ReservationEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

 private List<String> extractPetName(ReservationEntity entity){
  return entity.getReservationPets().stream()
       .map(rp -> rp
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
public ReservationDetailDto DetailDto(ReservationEntity entity){

  List<Long> petsId = entity.getReservationPets().stream()
      .map(rp -> rp.getPets().getId())
      .toList();

   return ReservationDetailDto.builder()
       .id(entity.getId())
       .customerId(entity.getCustomer().getId())
       .customerName(entity.getCustomer().getName())
       .customerPhone(entity.getCustomer().getPhone())
       .menuName(entity.getMenu().getName())
       .petsId(petsId)
       .petsName(extractPetName(entity))
       .startAt(entity.getStartAt())
       .endAt(entity.getEndAt())
       .createdAt(entity.getCreatedAt())
       .updatedAt(entity.getUpdatedAt())
       .build();
}
}
