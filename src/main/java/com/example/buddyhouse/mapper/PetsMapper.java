package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.dto.PetsListDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class PetsMapper {

  private final CustomerRepository customerRepository;

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
    dto.setDeleted(entity.isDeleted());
    return dto;
  }
  //DTOからEntityへ変換（全カラム）
  public PetsEntity toEntity(PetsDto dto)  {
    PetsEntity entity = new PetsEntity();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setBreed(dto.getBreed());
    entity.setWeight(dto.getWeight());
    entity.setAge(dto.getAge());
    entity.setFeature(dto.getFeature());
    entity.setDeleted(dto.isDeleted());
    CustomerEntity customerRef = customerRepository.getReferenceById(dto.getCustomerId());
    entity.setCustomer(customerRef);

    return entity;
  }
  //EntityからDtoに変換（ペットリスト用）
  public PetsListDto toListDto(PetsEntity entity) {
    PetsListDto dto = new PetsListDto();
    dto.setId(entity.getId());
    dto.setCustomerId(entity.getCustomer().getId());
    dto.setName(entity.getName()) ;
    dto.setBreed(entity.getBreed());
    dto.setWeight(entity.getWeight());
    return dto;
  }
}
