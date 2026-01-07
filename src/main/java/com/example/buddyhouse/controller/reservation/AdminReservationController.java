package com.example.buddyhouse.controller.reservation;

import com.example.buddyhouse.dto.reservation.ReservationCancelDto;
import com.example.buddyhouse.dto.reservation.ReservationDetailDto;
import com.example.buddyhouse.dto.reservation.ReservationListDto;
import com.example.buddyhouse.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "07.Admin　-　Reservations", description = "予約管理(管理者)")
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
  @Operation(summary = "予約一覧取得", description = "日付を指定して予約リストを絞り込みます。指定しない場合は全件取得します。")
  @GetMapping
  public List<ReservationListDto> getReservationList(
      @Parameter(
          description = "絞り込みたい日付（任意）",
          example = "2025-11-24"
      )
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

    return reservationService.getReservationList(date);
  }

  @GetMapping("/{reservationId}")
  public ReservationDetailDto getDetailReservation(@PathVariable Long reservationId) {
    return reservationService.getDetailReservation(reservationId);
  }

  @GetMapping("customers/{customerId}")
  public List<ReservationListDto> getReservationListByCustomer(@PathVariable Long customerId) {
    return reservationService.getMyReservations(customerId);
  }

}
