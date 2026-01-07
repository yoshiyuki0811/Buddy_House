package com.example.buddyhouse.controller.reservaitonFrame;

import com.example.buddyhouse.dto.resrvationFrame.FrameListDto;
import com.example.buddyhouse.service.ReservationFrameService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "08.ReservationFrames", description = "予約枠の参照(会員)")
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
  public List<FrameListDto> getReservationFrameByDate(
      @Parameter(
          description = "参照したい予約日（yyyy-MM-dd形式）",
          example = "2025-11-24"
      )
      @RequestParam("dateTime")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTime) {

    return reservationFrameService.getReservationFrameListByDate(dateTime);
  }




}
