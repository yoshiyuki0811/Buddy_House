package com.example.buddyhouse.controller;


import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.service.PetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetsController {

  private final PetsService petsService;

  @PostMapping
  public ResponseEntity<PetsDto> createPets(@PathVariable Long customerId,@RequestBody PetsDto dto) {
    PetsDto saved = petsService.createPets(dto);
    return ResponseEntity.ok(saved);
  }


}
