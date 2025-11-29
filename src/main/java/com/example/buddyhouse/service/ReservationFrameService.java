package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.FrameDto;
import com.example.buddyhouse.dto.FrameListDto;
import com.example.buddyhouse.dto.FrameRequestDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.mapper.FrameMapper;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 予約枠ドメインを扱うサービスクラスです。
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationFrameService {

  private final ReservationFrameRepository reservationFrameRepository;
  private final FrameMapper reservationFrameMapper;

  //予約枠の作成
  public FrameDto createReservationFrame(FrameRequestDto requestDto) {
    ReservationFrameEntity entity = reservationFrameMapper.toEntity(requestDto);
    ReservationFrameEntity saved = reservationFrameRepository.save(entity);
    return reservationFrameMapper.toDto(saved);
  }

  //予約枠の一覧取得
  public List<FrameListDto> getReservationFrameList() {
    List<ReservationFrameEntity> entity = reservationFrameRepository.findAll();

    return entity
        .stream().map(reservationFrameMapper::toListDto)
        .toList();

  }

  //予約一覧の日付検索
  public List<FrameListDto> getReservationFrameListByDate(LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = date.plusDays(1).atStartOfDay();

    return reservationFrameRepository.findByStartAtBetween(start, end)
        .stream().map(reservationFrameMapper::toListDto)
        .toList();

  }

  //予約枠の削除
  public void deletedReservationFrameById(Long id) {
    ReservationFrameEntity entity = reservationFrameRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("予約枠がみつかりません。"));
    entity.delete();

  }

  //予約枠のクローズ処理
  public void closeReservationFrame(Long id) {
    ReservationFrameEntity entity = reservationFrameRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("予約枠が見つかりません。"));
    entity.frameClose();
  }
}
