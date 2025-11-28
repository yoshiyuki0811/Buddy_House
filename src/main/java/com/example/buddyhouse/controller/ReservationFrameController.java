package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.FrameDto;
import com.example.buddyhouse.dto.FrameListDto;
import com.example.buddyhouse.dto.FrameRequestDto;
import com.example.buddyhouse.service.ReservationFrameService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservationFrame")
@RequiredArgsConstructor
public class ReservationFrameController {

  private final ReservationFrameService reservationFrameService;

  @PostMapping
  public ResponseEntity<FrameDto> createReservationFrame(@RequestBody FrameRequestDto requestDto) {
    FrameDto saved = reservationFrameService.createReservationFrame(requestDto);
    return ResponseEntity.ok(saved);
  }

  @GetMapping
  public ResponseEntity<List<FrameListDto>> getAllReservationFrame() {
    List<FrameListDto> dtoList = reservationFrameService.getReservationFrameList();
    return ResponseEntity.ok(dtoList);
  }

  @GetMapping("/by-date")
  public ResponseEntity<List<FrameListDto>> getReservationFrameByDate(@RequestParam("dateTime")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTime) {
    return ResponseEntity.ok(reservationFrameService.getReservationFrameListByDate(dateTime));
  }


  @PatchMapping("/{id}/deleted")
  public ResponseEntity<Void> deletedReservationFrameById(@PathVariable Long id) {
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/close")
  public ResponseEntity<Void> closeReservationFrame(@PathVariable Long id) {
    return ResponseEntity.noContent().build();
  }


}
