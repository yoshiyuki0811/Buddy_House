package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.mapper.ReservationFrameMapper;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationFrameService {

  private final ReservationFrameRepository reservationFrameRepository;
  private final ReservationFrameMapper reservationFrameMapper;

  public ReservationFrameDto createReservationFrame(ReservationFrameDto dto) {
    ReservationFrameEntity entity = reservationFrameMapper.toEntity(dto);
    ReservationFrameEntity saved = reservationFrameRepository.save(entity);
    return reservationFrameMapper.toDto(saved);
  }

}
