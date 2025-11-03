package com.example.buddyhouse.service;


import com.example.buddyhouse.dto.CustomersListDto;
import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.dto.PetsListDto;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.mapper.PetsMapper;
import com.example.buddyhouse.repository.PetsRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetsService {
  private final PetsRepository petsRepository;
  private final PetsMapper petsMapper;

  //ペットの登録
  @Transactional
  public PetsDto createPets(PetsDto dto) {
    PetsEntity entity = petsMapper.toEntity(dto);
    PetsEntity saved = petsRepository.save(entity);// DB保存
    return petsMapper.toDto(saved);
  }

  public List<PetsListDto> getPetsList(){

    return petsRepository.findAllForList();
  }

}
