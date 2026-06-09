package com.example.buddyhouse.controller.pet;


import com.example.buddyhouse.dto.pet.PetDetailDto;
import com.example.buddyhouse.dto.pet.PetRequestDto;
import com.example.buddyhouse.dto.pet.PetsDto;
import com.example.buddyhouse.dto.pet.PetsListDto;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.PetsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "04.Pets", description = "ペット関連操作(会員)")
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetsController {

  private final PetsService petsService;
  private final AuthService authService;

  @PostMapping("/me")
  @ResponseStatus(HttpStatus.CREATED)
  public void createMyPet(
      @AuthenticationPrincipal UserDetails userDetails,
      @Valid @RequestBody PetRequestDto dto
  ) {
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    petsService.createPets(customerId, dto);
  }

  @GetMapping("/me")
  public List<PetsListDto> getMyPets(@AuthenticationPrincipal UserDetails userDetails){
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    return petsService.getPetsListByCustomerId(customerId);
  }
  @GetMapping("/{id}")
  public PetDetailDto getDetailMyPet(
      @AuthenticationPrincipal UserDetails userDetails
      ,@PathVariable Long id)
  {
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    return petsService.getPetsById(customerId,id);
  }

  @PatchMapping("/me/{id}")
  public ResponseEntity<PetsDto> updateMyPet(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long id,
      @Valid @RequestBody PetRequestDto dto
  ) {
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    PetsDto updated = petsService.updatePetsByIdForCustomer(customerId, id, dto);
    return ResponseEntity.ok(updated);
  }






}
