package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.ReservationFrameDto;
import com.example.buddyhouse.dto.ReservationFrameListDto;
import com.example.buddyhouse.service.ReservationFrameService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservationFrame")
@RequiredArgsConstructor
public class ReservationFrameController {

  private final ReservationFrameService reservationFrameService;

  @PostMapping
  public ResponseEntity<ReservationFrameDto> createReservationFrame(@RequestBody ReservationFrameDto dto){
    ReservationFrameDto saved = reservationFrameService.createReservationFrame(dto);
    return ResponseEntity.ok(saved);
  }
  @GetMapping
  public  ResponseEntity<List<ReservationFrameListDto>> getAllReservationFrame(){
    List<ReservationFrameListDto> dtoList = reservationFrameService.getReservationFrameList();
    return ResponseEntity.ok(dtoList);
  }


  @PatchMapping("/{id}/deleted")
  public ResponseEntity<ReservationFrameDto> deletedReservationFrameById(@PathVariable Long id){
    ReservationFrameDto deleted = reservationFrameService.deletedReservationFrameById(id);
    return ResponseEntity.ok(deleted);
  }

}
