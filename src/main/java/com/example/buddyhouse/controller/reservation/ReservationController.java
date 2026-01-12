package com.example.buddyhouse.controller.reservation;

import com.example.buddyhouse.dto.reservation.ReservationDto;
import com.example.buddyhouse.dto.reservation.ReservationListDto;
import com.example.buddyhouse.dto.reservation.ReservationRequestDto;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
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
  @Operation(
      summary = "予約の作成（ログイン中の顧客）",
      description = """
ログイン中の顧客が、自分のペットを指定して予約を作成します。
このAPIでは customerId は指定せず、JWTに含まれるユーザー情報から自動的に顧客を判定します。

【事前に登録されている前提データ】

・予約枠（ReservationFrame）
  1 = OVERNIGHT（宿泊用の予約枠）
  2 = DAYCARE（日帰り用の予約枠）

・ペット
  petId = 3, 4 のペットが、ログイン中の顧客により事前に登録されています。

・メニュー
  menuId = 1 … 日帰りメニュー（DAYCARE）
  menuId = 2 … 宿泊メニュー（OVERNIGHT）

【入力例（宿泊予約の作成）】
{
  "reservationFrameId": 1,
  "menuId": 2,
  "petIds": [3, 4]
}

この例は、
「petId 3 と 4 の2頭を、予約枠1（宿泊）に対して、
menuId 2（宿泊メニュー）で予約する」
ことを意味します。
"""
  )

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
