package com.example.buddyhouse.controller.pet;

import com.example.buddyhouse.dto.pet.PetRequestDto;
import com.example.buddyhouse.dto.pet.PetsDto;
import com.example.buddyhouse.dto.pet.PetsListDto;
import com.example.buddyhouse.service.PetsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/pets")
@RequiredArgsConstructor
public class AdminPetsController {
  private final PetsService petsService;

  @PostMapping("/{customerId}/pets")
  @ResponseStatus(HttpStatus.CREATED)
  public void createPetsForCustomer(@PathVariable Long customerId,@RequestBody PetRequestDto dto) {
    petsService.createPets(customerId,dto);

  }

  @GetMapping
  public List<PetsListDto> getPetsList() {
    return petsService.getPetsList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PetsDto> getPetsById(@PathVariable Long id) {
    PetsDto pets = petsService.getPetsById(id);
    return ResponseEntity.ok(pets);
  }

  @GetMapping("/{customerId}/pets")
  public List<PetsListDto> getPetsListByCustomerId(@PathVariable Long customerId) {
    return petsService.getPetsListByCustomerId(customerId);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<PetsDto> updatePetsById(@PathVariable Long id, @RequestBody PetsDto dto) {
    PetsDto update = petsService.updatePetsById(id, dto);
    return ResponseEntity.ok(update);
  }

  @PatchMapping("/{id}/deleted")
  public ResponseEntity<PetsDto> deletedPetsById(@PathVariable Long id) {
    petsService.deletePetsById(id);
    return ResponseEntity.noContent().build();
  }
}
