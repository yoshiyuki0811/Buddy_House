package com.example.buddyhouse.dto;

import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.entity.ReservationEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPetDto {

  private  Long id;
  private PetsEntity pets;
  private ReservationEntity reservation;
  private MenuEntity menu;
  private boolean deleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;




}
