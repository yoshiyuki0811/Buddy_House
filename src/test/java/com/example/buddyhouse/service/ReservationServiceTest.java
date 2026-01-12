package com.example.buddyhouse.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.buddyhouse.repository.ReservationFrameRepository;
import com.example.buddyhouse.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private ReservationFrameRepository reservationFrameRepository;

  @InjectMocks
  private ReservationService reservationService;


  @Test
  void 予約のキャンセル処理をされたときにReservationFrameのuseDogがへるか(){



  }

}