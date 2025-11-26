package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.ReservationCancelDto;
import com.example.buddyhouse.dto.ReservationDto;
import com.example.buddyhouse.dto.ReservationListDto;
import com.example.buddyhouse.dto.ReservationRequestDto;
import com.example.buddyhouse.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping
  public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
    ReservationDto saved = reservationService.createReservation(requestDto);
    return ResponseEntity.ok(saved);
  }

  @PatchMapping("/{reservationId}/cancel")
  public ResponseEntity<ReservationCancelDto> cancelReservation(@PathVariable Long reservationId) {
    ReservationCancelDto cancelled = reservationService.cancelReservation(reservationId);
    return ResponseEntity.ok(cancelled);
  }
@GetMapping
  public ResponseEntity<List<ReservationListDto>> getReservationList(){
    List<ReservationListDto> reservationList =reservationService.getReservationList();
    return ResponseEntity.ok(reservationList);
}

}
