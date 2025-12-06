package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.FrameListDto;
import com.example.buddyhouse.service.ReservationFrameService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservationFrame")
@RequiredArgsConstructor
public class ReservationFrameController {

  private final ReservationFrameService reservationFrameService;



  @GetMapping
  public List<FrameListDto> getAllReservationFrame() {
    return reservationFrameService.getReservationFrameList();
  }

  @GetMapping("/by-date")
  public List<FrameListDto> getReservationFrameByDate(@RequestParam("dateTime")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTime) {
    return reservationFrameService.getReservationFrameListByDate(dateTime);
  }




}
