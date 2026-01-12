package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.reservation.ReservationCancelDto;
import com.example.buddyhouse.dto.reservation.ReservationDetailDto;
import com.example.buddyhouse.dto.reservation.ReservationDto;
import com.example.buddyhouse.dto.reservation.ReservationRequestDto;
import com.example.buddyhouse.dto.reservation.ReservationListDto;
import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.entity.ReservationPetEntity;
import com.example.buddyhouse.entity.UserEntity;
import com.example.buddyhouse.enums.ReservationStatus;
import com.example.buddyhouse.mapper.ReservationMapper;
import com.example.buddyhouse.repository.MenusRepository;
import com.example.buddyhouse.repository.PetsRepository;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import com.example.buddyhouse.repository.ReservationRepository;
import com.example.buddyhouse.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
/**
 * 予約ドメインを扱うサービスクラスです。
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationFrameRepository reservationFrameRepository;
  private final MenusRepository menusRepository;
  private final PetsRepository petsRepository;
  private final UserRepository userRepository;
  private final ReservationMapper reservationMapper;

  /**
   * 予約を作成し、ペット数に応じて予約枠の利用数を更新します。
   */
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

    //ログイン中ユーザー => user を取得
    String email = SecurityContextHolder.getContext().getAuthentication().getName(); // JWTのsub/email想定
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("顧客が見つかりません。"));

    MenuEntity menu = menusRepository.findById(requestDto.getMenuId())
        .orElseThrow(() -> new RuntimeException("メニューが見つかりません。"));
//この予約に来る犬リスト
    List<PetsEntity> pets = petsRepository.findAllById(requestDto.getPetIds());
//予約のチェック
    ReservationEntity reservation = ReservationEntity.create(
        frame, user, menu, pets);


//ペットの数だけDtoを生成→Entityにセーブ
    for (PetsEntity pet : pets) {
      //中間テーブルの生成
      ReservationPetEntity reservationPet = ReservationPetEntity.create(reservation, pet);
      reservation.getReservationPets().add(reservationPet);
    }
    //データベースに保存( cascade = ALL設定しているため、reservationPetもsaveされる。)
    reservationRepository.save(reservation);

    return reservationMapper.toDto(reservation);


  }

  /**
   * 予約のキャンセル処理をしてペット数応じて予約数を更新します。
   */
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

    return reservationMapper.toCancelDto(saved);


  }

  public List<ReservationListDto> getReservationList(LocalDate date){
    if (date==null){
    List<ReservationEntity> reservationList = reservationRepository.findAll();
    return  reservationList.stream()
        .map(reservationMapper::toListDto)
        .toList();
    }
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();
    List<ReservationEntity> reservationDate =reservationRepository.findByStartAtBetween(start, end);
    return reservationDate.stream()
        .map(reservationMapper::toListDto)
        .toList();

  }
  public ReservationDetailDto getDetailReservation(Long reservationId){
    ReservationEntity reservation =reservationRepository.findById(reservationId)
        .orElseThrow(() -> new RuntimeException("予約が存在しません。"));
    return reservationMapper.DetailDto(reservation);

  }
public List<ReservationListDto> getMyReservations(Long customerId){
List<ReservationEntity> reservations =reservationRepository.findByCustomerIdOrderByStartAtDesc(customerId);
return reservations.stream()
    .map(reservationMapper::toListDto)
    .toList();







}

}


