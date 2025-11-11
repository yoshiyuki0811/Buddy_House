package com.example.buddyhouse.controller;





import com.example.buddyhouse.dto.PetsDto;
import com.example.buddyhouse.dto.PetsListDto;
import com.example.buddyhouse.service.PetsService;
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
  @GetMapping("/{id}")
  public ResponseEntity<PetsDto> getPetsById(@PathVariable Long id){
    PetsDto pets = petsService.getPetsById(id);
    return ResponseEntity.ok(pets);
  }

  @GetMapping("/{customerId}/pets")
  public ResponseEntity<List<PetsListDto>> getPetsListById(@PathVariable Long customerId) {
    List<PetsListDto> petsList = petsService.getPetsListById(customerId);
    return ResponseEntity.ok(petsList);
  }
  @PatchMapping("/{id}")
  public ResponseEntity<PetsDto> updatePetsById(@PathVariable Long id,@RequestBody PetsDto dto) {
    PetsDto update = petsService.updatePetsById(id,dto);
    return ResponseEntity.ok(update);
  }
  @PatchMapping("/{id}/deleted")
public  ResponseEntity<PetsDto> deletedPetsById(@PathVariable Long id){
    PetsDto petsDeleted = petsService.deletePetsById(id);
    return ResponseEntity.ok(petsDeleted);
  }
}
