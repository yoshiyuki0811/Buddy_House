package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.pet.PetDetailDto;
import com.example.buddyhouse.dto.pet.PetRequestDto;
import com.example.buddyhouse.dto.pet.PetsDto;
import com.example.buddyhouse.dto.pet.PetsListDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.PetsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class PetsMapper {

  // Entity → DTO に変換するコンストラクタ(全カラム)
  public PetsDto toDto(PetsEntity entity) {
    PetsDto dto = new PetsDto();
    dto.setId(entity.getId());
    dto.setCustomerId(entity.getCustomer().getId());
    dto.setName(entity.getName()) ;
    dto.setBreed(entity.getBreed());
    dto.setWeight(entity.getWeight());
    dto.setAge(entity.getAge());
    dto.setFeature(entity.getFeature());
    return dto;
  }
  //DTOからEntityへ変換（全カラム）
  public PetsEntity toEntity(CustomerEntity customer, PetRequestDto dto)  {
   return PetsEntity.builder()
       .customer(customer)
       .name(dto.getName())
       .breed(dto.getBreed())
       .age(dto.getAge())
       .weight(dto.getWeight())
       .feature(dto.getFeature())
       .build();
  }
  //EntityからDtoに変換（ペットリスト用）
  public PetsListDto toListDto(PetsEntity entity) {
    PetsListDto dto = new PetsListDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName()) ;
    dto.setBreed(entity.getBreed());
    dto.setWeight(entity.getWeight());
    return dto;
  }

  public PetDetailDto petDetailDto(PetsEntity entity){
    return PetDetailDto.builder()
        .name(entity.getName())
        .breed(entity.getBreed())
        .weight(entity.getWeight())
        .age(entity.getAge())
        .feature(entity.getFeature())
        .createdAt(entity.getCreatedAt())
        .build();


  }
}
