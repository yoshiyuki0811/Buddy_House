package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.dto.ReservationFrameListDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.mapper.ReservationFrameMapper;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationFrameService {

  private final ReservationFrameRepository reservationFrameRepository;
  private final ReservationFrameMapper reservationFrameMapper;
//予約枠の作成
  public ReservationFrameDto createReservationFrame(ReservationFrameDto dto) {
    ReservationFrameEntity entity = reservationFrameMapper.toEntity(dto);
    ReservationFrameEntity saved = reservationFrameRepository.save(entity);
    return reservationFrameMapper.toDto(saved);
  }
//予約枠の一覧取得
  public List<ReservationFrameListDto> getReservationFrameList() {
    List<ReservationFrameEntity> entity =reservationFrameRepository.findAll();

    return entity
        .stream().map(reservationFrameMapper::toListDto)
        .toList();

  }

  //予約一覧の日付検索
  public List<ReservationFrameListDto> getReservationFrameListByDate(LocalDateTime date) {
    LocalDateTime start =date.toLocalDate().atStartOfDay();
    LocalDateTime end = date.toLocalDate().atTime(23,59,59,999999999);

    return reservationFrameRepository.findByStartAtBetween(start,end)
        .stream().map(reservationFrameMapper::toListDto)
        .toList();

  }

  //予約枠の削除
  public ReservationFrameDto deletedReservationFrameById(Long id){
    ReservationFrameEntity entity = reservationFrameRepository.findById(id)
        .orElseThrow(()->new RuntimeException("予約枠がみつかりません。"));
    entity.delete();

    return reservationFrameMapper.toDto(reservationFrameRepository.save(entity));
  }
  //予約枠のクローズ処理
public ReservationFrameDto closeReservationFrame(Long id){
ReservationFrameEntity entity = reservationFrameRepository.findById(id)
    .orElseThrow(()->new RuntimeException("予約枠が見つかりません。"));
entity.frameClose();
return reservationFrameMapper.toDto(reservationFrameRepository.save(entity));
}
}
