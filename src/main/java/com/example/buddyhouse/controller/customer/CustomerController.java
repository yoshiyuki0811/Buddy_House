package com.example.buddyhouse.controller.customer;

import com.example.buddyhouse.dto.customer.CustomerDetailDto;
import com.example.buddyhouse.dto.customer.CustomerDto;
import com.example.buddyhouse.security.CustomUserDetails;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final AuthService authService;


  @PostMapping
  public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto) {
    CustomerDto saved = customerService.createCustomer(dto);
    return ResponseEntity.ok(saved);
  }
  @GetMapping("/me")
  public CustomerDetailDto getCustomerMe(@AuthenticationPrincipal CustomUserDetails user){
    Long customerId = authService.getLoggedInCustomerId(user.getUsername());
    return customerService.getCustomersById(customerId);
  }

}


