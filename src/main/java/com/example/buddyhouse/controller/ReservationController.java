package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.dto.ReservationRequestDto;
import com.example.buddyhouse.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping
  public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
    ReservationDto saved = reservationService.createReservation(requestDto);
    return ResponseEntity.ok(saved);
  }



}
