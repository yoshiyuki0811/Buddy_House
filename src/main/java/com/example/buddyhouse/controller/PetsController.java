package com.example.buddyhouse.controller;


import com.example.buddyhouse.dto.CustomersListDto;
import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.dto.PetsListDto;
import com.example.buddyhouse.service.PetsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetsController {
  private final PetsService petsService;

  @PostMapping
  public ResponseEntity<PetsDto> createPets(@RequestBody PetsDto dto){
    PetsDto saved = petsService.createPets(dto);
    return ResponseEntity.ok(saved);
  }
  @GetMapping
  public ResponseEntity<List<PetsListDto>> getPetsList(){
    List<PetsListDto> pets =petsService.getPetsList();
    return ResponseEntity.ok(pets);
  }


}
