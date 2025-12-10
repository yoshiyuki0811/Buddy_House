package com.example.buddyhouse.controller.pet;


import com.example.buddyhouse.dto.pet.PetRequestDto;
import com.example.buddyhouse.dto.pet.PetsListDto;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.PetsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
      @RequestBody PetRequestDto dto
  ) {
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    petsService.createPets(customerId, dto);
  }

  @GetMapping
  public List<PetsListDto> getMyPets(@AuthenticationPrincipal UserDetails userDetails){
    Long customerId = authService.getLoggedInCustomerId(userDetails.getUsername());
    return petsService.getPetsListByCustomerId(customerId);



  }






}
