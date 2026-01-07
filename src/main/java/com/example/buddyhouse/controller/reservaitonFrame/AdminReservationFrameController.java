package com.example.buddyhouse.controller.reservaitonFrame;

import com.example.buddyhouse.dto.resrvationFrame.FrameDto;
import com.example.buddyhouse.dto.resrvationFrame.FrameRequestDto;
import com.example.buddyhouse.service.ReservationFrameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "09.Admin　-　ReservationFrame", description = "予約枠管理(管理者)")
@RestController
@RequestMapping("/api/admin/reservationFrame")
@RequiredArgsConstructor
public class AdminReservationFrameController {

  private final ReservationFrameService reservationFrameService;

  @PostMapping
  public ResponseEntity<FrameDto> createReservationFrame(@RequestBody FrameRequestDto requestDto) {
    FrameDto saved = reservationFrameService.createReservationFrame(requestDto);
    return ResponseEntity.ok(saved);
  }
  @PatchMapping("/{id}/deleted")
  public ResponseEntity<Void> deletedReservationFrameById(@PathVariable Long id) {
    reservationFrameService.deletedReservationFrameById(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/close")
  public ResponseEntity<Void> closeReservationFrame(@PathVariable Long id) {
    reservationFrameService.closeReservationFrame(id);
    return ResponseEntity.noContent().build();
  }

}
