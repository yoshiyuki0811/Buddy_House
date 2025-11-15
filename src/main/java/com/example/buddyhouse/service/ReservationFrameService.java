package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.dto.ReservationFrameListDto;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.mapper.ReservationFrameMapper;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationFrameService {

  private final ReservationFrameRepository reservationFrameRepository;
  private final ReservationFrameMapper reservationFrameMapper;

  public ReservationFrameDto createReservationFrame(ReservationFrameDto dto) {
    ReservationFrameEntity entity = reservationFrameMapper.toEntity(dto);
    ReservationFrameEntity saved = reservationFrameRepository.save(entity);
    return reservationFrameMapper.toDto(saved);
  }

  public List<ReservationFrameListDto> getReservationFrameList() {
    List<ReservationFrameEntity> entity =reservationFrameRepository.findAll();

    return entity
        .stream().map(reservationFrameMapper::toListDto)
        .toList();

  }


  public ReservationFrameDto deletedReservationFrameById(Long id){
    ReservationFrameEntity entity = reservationFrameRepository.findById(id)
        .orElseThrow(()->new RuntimeException("予約枠がみつかりません。"));
    entity.setDeleted(true);
    ReservationFrameEntity deleted =reservationFrameRepository.save(entity);
    return reservationFrameMapper.toDto(deleted);


  }

}
