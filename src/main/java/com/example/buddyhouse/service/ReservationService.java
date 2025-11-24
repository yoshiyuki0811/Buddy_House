package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.ReservationCancelDto;
import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.dto.ReservationRequestDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.entity.ReservationPetEntity;
import com.example.buddyhouse.enums.ReservationStatus;
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

  @Transactional
  public ReservationDto createReservation(ReservationRequestDto requestDto) {

    //フロントから来たPetsIdが空じゃないかチェック
    List<Long> petsIds = requestDto.getPetIds();
    if (petsIds == null || petsIds.isEmpty()) {
      throw new IllegalArgumentException("予約するペットが指定されていません");
    }
//フロントから来た情報をEntityと照合して取ってくる
    ReservationFrameEntity frame = reservationFrameRepository.findById(requestDto.getReservationFrameId())
        .orElseThrow(() -> new RuntimeException("予約枠が見つかりません"));

    CustomerEntity customer = customerRepository.findById(requestDto.getCustomerId())
        .orElseThrow(() -> new RuntimeException("顧客が見つかりません。"));

    MenuEntity menu = menusRepository.findById(requestDto.getMenuId())
        .orElseThrow(() -> new RuntimeException("メニューが見つかりません。"));
//この予約に来る犬リスト
    List<PetsEntity> pets = petsRepository.findAllById(requestDto.getPetIds());
//予約のチェック
    ReservationEntity reservation = ReservationEntity.create(
        frame, customer, menu, pets);
    //データベースに保存
    reservationRepository.save(reservation);

//ペットの数だけDtoを生成→Entityにセーブ
    for (Long petId : requestDto.getPetIds()) {
      PetsEntity pet = petsRepository.findById(petId)
          .orElseThrow(() -> new RuntimeException("ペットが存在しません"));
      //中間テーブルの生成
      ReservationPetEntity reservationPet = ReservationPetEntity.create(reservation, pet);
      reservationPetRepository.save(reservationPet);
      reservation.getReservationPets().add(reservationPet);
    }
    return reservationMapper.toDto(reservation);


  }

  @Transactional
  public ReservationCancelDto cancelReservation(Long reservationId) {
    //予約のどの予約のなのかチェック
    ReservationEntity reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new RuntimeException("予約が見つかりません。id=" + reservationId));

    if (reservation.getStatus() == ReservationStatus.CANCELLED) {
      throw new IllegalStateException("すでにキャンセルされています。 id=" + reservationId);
    }
//キャンセル分の予約枠の確保
    ReservationFrameEntity frame = reservation.getFrame();
    int numDogs = reservation.getReservationPets().size();
    frame.removeDogs(numDogs);
    //ステータスをキャンセルに変更
    reservation.cancel();
    ReservationEntity saved = reservationRepository.save(reservation);

    reservation.getReservationPets().clear();

    return reservationMapper.toCancelDto(saved);


  }


}


