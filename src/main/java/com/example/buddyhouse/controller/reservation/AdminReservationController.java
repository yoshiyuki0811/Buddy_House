package com.example.buddyhouse.controller.reservation;

import com.example.buddyhouse.dto.reservation.ReservationCancelDto;
import com.example.buddyhouse.dto.reservation.ReservationDetailDto;
import com.example.buddyhouse.dto.reservation.ReservationListDto;
import com.example.buddyhouse.service.ReservationService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reservation")
@RequiredArgsConstructor
public class AdminReservationController {

  private final ReservationService reservationService;

  @PatchMapping("/{reservationId}/cancel")
  public ResponseEntity<ReservationCancelDto> cancelReservation(@PathVariable Long reservationId) {
    ReservationCancelDto cancelled = reservationService.cancelReservation(reservationId);
    return ResponseEntity.ok(cancelled);
  }

  @GetMapping
  public List<ReservationListDto> getReservationList(@RequestParam(required = false)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  LocalDate date) {
    return reservationService.getReservationList(date);
  }

  @GetMapping("/{reservationId}")

  public ReservationDetailDto getDetailReservation(@PathVariable Long reservationId) {
    return reservationService.getDetailReservation(reservationId);
  }


}
