package com.example.buddyhouse.controller.reservaitonFrame;

import com.example.buddyhouse.dto.resrvationFrame.FrameDto;
import com.example.buddyhouse.dto.resrvationFrame.FrameRequestDto;
import com.example.buddyhouse.service.ReservationFrameService;
import io.swagger.v3.oas.annotations.Operation;
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
  @Operation(
      summary = "予約枠の作成",
      description = """
管理者がペットホテルの予約枠（ReservationFrame）を登録します。

予約枠は「いつから」「いつまで」「何頭まで預かれるか」を表す業務データです。
宿泊（OVERNIGHT）の場合は日をまたぐ期間、
日帰り（DAYCARE）の場合は同一日の時間帯を指定してください。

【入力例】
{
  "reservationType": "OVERNIGHT",
  "startAt": "2026-01-12T15:00",
  "endAt":   "2026-01-13T10:00",
  "maxDogs": 5
}

この例は「1月12日 15:00 〜 1月13日 10:00 の宿泊枠を、最大5頭まで受け付ける」
ことを意味します。
"""
  )

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
