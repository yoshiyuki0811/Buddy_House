package com.example.buddyhouse.service;


import com.example.buddyhouse.dto.pet.PetDetailDto;
import com.example.buddyhouse.dto.pet.PetRequestDto;
import com.example.buddyhouse.dto.pet.PetsDto;
import com.example.buddyhouse.dto.pet.PetsListDto;


import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.mapper.PetsMapper;
import com.example.buddyhouse.repository.CustomerRepository;
import com.example.buddyhouse.repository.PetsRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * ペットドメインを扱うサービスクラスです。
 */
@Service
@RequiredArgsConstructor
public class PetsService {

private final CustomerRepository customerRepository;
  private final PetsRepository petsRepository;
  private final PetsMapper petsMapper;

  //ペットの登録
  @Transactional
  public void createPets(Long customerId,PetRequestDto dto) {
    CustomerEntity customer =customerRepository.findById(customerId)
        .orElseThrow(() -> new IllegalArgumentException("顧客が存在しません。"));

    PetsEntity pet =petsMapper.toEntity(customer,dto);

    petsRepository.save(pet);
  }

  //現在登録されているペットの一覧を取る
  public List<PetsListDto> getPetsList() {
    List<PetsEntity> entity = petsRepository.findAll();

    return entity
        .stream().map(petsMapper::toListDto)
        .toList();

  }

  //ペットの詳細情報を取得（顧客用：所有者チェックあり）
  public PetDetailDto getPetsById(Long customerId, Long id) {
    PetsEntity entity = petsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ペットがみつかりません。"));

    if (!entity.getCustomer().getId().equals(customerId)) {
      throw new AccessDeniedException("このペットにアクセスする権限がありません");
    }
    return petsMapper.petDetailDto(entity);
  }

  //ペットの詳細情報を取得（管理者用：所有者チェックなし）
  public PetDetailDto getPetsByIdForAdmin(Long id) {
    PetsEntity entity = petsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ペットがみつかりません。"));
    return petsMapper.petDetailDto(entity);
  }

  //特定の顧客が登録したペットの一覧を取得
  public List<PetsListDto> getPetsListByCustomerId(Long customerId) {
    List<PetsEntity> entity = petsRepository.findAllByCustomerIdAndDeletedFalse(customerId);
    return entity.stream()
        .map(petsMapper::toListDto)
        .toList();
  }

  //ペット情報の編集、更新
  public PetsDto updatePetsById(Long id, PetsDto petsDto) {
    PetsEntity entity = petsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("顧客がみつかりません。"));
//変更があった箇所だけ更新する
    if (petsDto.getName() != null) {
      entity.setName(petsDto.getName());
    }
    if (petsDto.getBreed() != null) {
      entity.setBreed(petsDto.getBreed());
    }
    if (petsDto.getWeight() != null) {
      entity.setWeight(petsDto.getWeight());
    }
    if (petsDto.getAge() != null) {
      entity.setAge(petsDto.getAge());
    }
    if (petsDto.getFeature() != null) {
      entity.setFeature(petsDto.getFeature());
    }

    PetsEntity updated = petsRepository.save(entity);
    return petsMapper.toDto(updated);
  }

  //ペットテーブルの削除フラグをtrueに変換して登録
  @Transactional
  public void deletePetsById(Long id) {
    PetsEntity entity = petsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ペットがみつかりません。"));
    entity.setDeleted(true);
    petsRepository.save(entity);



  }
}
