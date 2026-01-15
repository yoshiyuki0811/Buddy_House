package com.example.buddyhouse.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.buddyhouse.dto.reservation.ReservationCancelDto;
import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.entity.ReservationPetEntity;
import com.example.buddyhouse.enums.ReservationStatus;
import com.example.buddyhouse.mapper.ReservationMapper;
import com.example.buddyhouse.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private ReservationMapper reservationMapper;

  @InjectMocks
  private ReservationService reservationService;


  @Test
  @DisplayName("予約をキャンセルした場合、予約枠の使用頭数が減る")
  void cancel_shouldDecreaseUsedDogs(){

    //Arrange(準備)

    Long reservationId = 1L;

    ReservationFrameEntity frame = ReservationFrameEntity.builder()
        .maxDogs(10)
        .usedDogs(5)
        .build();

    ReservationPetEntity rp1 = ReservationPetEntity.builder().build();
    ReservationPetEntity rp2 = ReservationPetEntity.builder().build();

    ReservationEntity reservation = ReservationEntity.builder()
        .id(reservationId)
        .status(ReservationStatus.RESERVED)
        .frame(frame)
        .reservationPets(List.of(rp1,rp2))
        .build();

    when(reservationRepository.findById(reservationId))
        .thenReturn(Optional.of(reservation));

    // save は「同じインスタンスを返す」で十分
    when(reservationRepository.save(any(ReservationEntity.class)))
        .thenAnswer(inv -> inv.getArgument(0));

    // mapper は戻り値が必要ならダミーを返す
    ReservationCancelDto dummyDto = mock(ReservationCancelDto.class);
    when(reservationMapper.toCancelDto(any(ReservationEntity.class)))
        .thenReturn(dummyDto);

    // ===== Act =====
    ReservationCancelDto result = reservationService.cancelReservation(reservationId);

    // ===== Assert =====
    assertEquals(3, frame.getUsedDogs());
    assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    assertSame (dummyDto, result);

    verify(reservationRepository).save(reservation);
    verify(reservationMapper).toCancelDto(reservation);

    //Act(実行)

    //Assert(検証)





  }

}