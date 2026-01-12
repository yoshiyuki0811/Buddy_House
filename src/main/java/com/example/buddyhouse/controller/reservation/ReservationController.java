package com.example.buddyhouse.controller.reservation;

import com.example.buddyhouse.dto.reservation.ReservationDto;
import com.example.buddyhouse.dto.reservation.ReservationListDto;
import com.example.buddyhouse.dto.reservation.ReservationRequestDto;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "06.Reservations", description = "予約操作(会員)")
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;
  private final AuthService authService;

  @PostMapping
  public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationRequestDto requestDto) {


    ReservationDto saved = reservationService.createReservation(requestDto);
    return ResponseEntity.ok(saved);
  }

  @GetMapping("/me")
  public List<ReservationListDto> getMyReservationList(@AuthenticationPrincipal UserDetails userDetails){
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    return reservationService.getMyReservations(customerId);
  }




}
