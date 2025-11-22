package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.dto.ReservationRequestDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.entity.ReservationPetEntity;
import com.example.buddyhouse.mapper.ReservationMapper;
import com.example.buddyhouse.repository.CustomerRepository;
import com.example.buddyhouse.repository.MenusRepository;
import com.example.buddyhouse.repository.PetsRepository;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import com.example.buddyhouse.repository.ReservationPetRepository;
import com.example.buddyhouse.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationFrameRepository reservationFrameRepository;
  private final CustomerRepository customerRepository;
  private final MenusRepository menusRepository;
  private final PetsRepository petsRepository;
  private final ReservationPetRepository reservationPetRepository;
  private final ReservationMapper reservationMapper;

  public ReservationDto createReservation(ReservationRequestDto requestDto) {
    System.out.println("frameId    = " + requestDto.getReservationFrameId());
    System.out.println("customerId = " + requestDto.getCustomerId());
    System.out.println("menuId     = " + requestDto.getMenuId());
    System.out.println("petsIds    = " + requestDto.getPetIds());

    //フロントから来たPetsIdが空じゃないかチェック
    List<Long> petsIds = requestDto.getPetIds();
    if (petsIds == null || petsIds.isEmpty()) {
      throw new IllegalArgumentException("予約するペットが指定されていません");
    }
//フロントから来た情報をEntityと照合して取ってくる
      ReservationFrameEntity frame = reservationFrameRepository.findById(requestDto.getReservationFrameId())
          .orElseThrow(() -> new RuntimeException("予約枠が見つかりません"));

      CustomerEntity customer = customerRepository.getReferenceById(requestDto.getCustomerId());

      MenuEntity menu = menusRepository.getReferenceById(requestDto.getMenuId());

    //この予約の犬数＝入ってきたペットの数（PetsIdsの数）
      int numDogs = requestDto.getPetIds().size();
      //予約枠の空きチェック
      frame.addUsedDogs(numDogs);
      reservationFrameRepository.save(frame);
      //Entityに登録
      ReservationEntity reservation = ReservationEntity.builder()
          .frame(frame)
          .customer(customer)
          .menu(menu)
          .startAt(requestDto.getStartAt())
          .endAt(requestDto.getEndAt())
          .build();
      reservationRepository.save(reservation);

//ペットの数だけDtoを生成→Entityにセーブ
      for (Long petId : requestDto.getPetIds()) {
        PetsEntity pet = petsRepository.findById(petId)
            .orElseThrow(() -> new RuntimeException("ペットが存在しません"));
        ReservationPetEntity reservationPet = ReservationPetEntity.builder()
            .reservation(reservation)
            .pets(pet)
            .build();
        reservationPetRepository.save(reservationPet);
        reservation.getReservationPets().add(reservationPet);
      }
      return reservationMapper.toDto(reservation);


    }



  }


