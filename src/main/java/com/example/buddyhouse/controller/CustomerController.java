package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;


  @PostMapping
  public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto) {
    CustomerDto saved = customerService.createCustomer(dto);
    return ResponseEntity.ok(saved);
  }
//  @GetMapping("/me")
//  public CustomerDetailDto getCustomerMe(@AuthenticationPrincipal CustomUserDetails user){
//    Long customerId = user.getcu
//  }

}


