package com.example.buddyhouse.service;




import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.dto.PetsListDto;

import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.mapper.PetsMapper;
import com.example.buddyhouse.repository.CustomerRepository;
import com.example.buddyhouse.repository.PetsRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetsService {

private  final CustomerRepository customerRepository;
  private final PetsRepository petsRepository;
  private final PetsMapper petsMapper;

  //ペットの登録
  @Transactional
  public PetsDto createPets(PetsDto dto) {
    PetsEntity entity = petsMapper.toEntity(dto);
    PetsEntity saved = petsRepository.save(entity);// DB保存
    return petsMapper.toDto(saved);
  }
//現在登録されているペットの一覧を取る
  public List<PetsListDto> getPetsList() {

    return petsRepository.findAllPetsList();

  }
  //ペットの詳細情報を取得
  public PetsDto getPetsById(Long id){
    PetsEntity entity = petsRepository.findById(id)
        .orElseThrow(()->new RuntimeException("ペットがみつかりません。"));
    return petsMapper.toDto(entity);
  }

  public List<PetsListDto> getPetsListById(Long customerId) {
     customerRepository.findById(customerId)
        .orElseThrow(()->new RuntimeException("顧客ががみつかりません。"));
    return petsRepository.findPetsByCustomerId(customerId);
  }
  public PetsDto updatePetsById(Long id,PetsDto petsDto) {
    PetsEntity entity  = petsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("顧客がみつかりません。"));

    if (petsDto.getName() != null) entity.setName(petsDto.getName());
    if (petsDto.getBreed() != null) entity.setBreed(petsDto.getBreed());
    if (petsDto.getWeight() != null) entity.setWeight(petsDto.getWeight());
    if (petsDto.getAge() != null) entity.setAge(petsDto.getAge());
    if (petsDto.getFeature() != null) entity.setFeature(petsDto.getFeature());



    PetsEntity updated = petsRepository.save(entity);
    return petsMapper.toDto(updated);
  }
}
