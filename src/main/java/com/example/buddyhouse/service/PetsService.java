package com.example.buddyhouse.service;


import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.mapper.PetsMapper;
import com.example.buddyhouse.repository.PetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetsService {
  private final PetsRepository petsRepository;
  private final PetsMapper petsMapper;

  //ペットの登録
  public PetsDto createPets(PetsDto dto) {
    PetsEntity entity = petsMapper.toEntity(dto);
    PetsEntity saved = petsRepository.save(entity);// DB保存
    return petsMapper.toDto(saved);
  }

}
