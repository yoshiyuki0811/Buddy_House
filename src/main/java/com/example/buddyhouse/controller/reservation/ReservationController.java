package com.example.buddyhouse.controller.reservation;

import com.example.buddyhouse.dto.reservation.ReservationDto;
import com.example.buddyhouse.dto.reservation.ReservationListDto;
import com.example.buddyhouse.dto.reservation.ReservationRequestDto;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation/me")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;
  private final AuthService authService;

  @PostMapping
  public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
    ReservationDto saved = reservationService.createReservation(requestDto);
    return ResponseEntity.ok(saved);
  }

  @GetMapping
  public List<ReservationListDto> getMyReservationList(@AuthenticationPrincipal UserDetails userDetails){
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    return reservationService.getMyReservations(customerId);
  }




}
