package com.example.buddyhouse.controller.customer;

import com.example.buddyhouse.dto.customer.CustomerDetailDto;
import com.example.buddyhouse.dto.customer.CustomerRequestDto;
import com.example.buddyhouse.security.CustomUserDetails;
import com.example.buddyhouse.service.AuthService;
import com.example.buddyhouse.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "02.Customers", description = "顧客関連操作(会員)")
@RestController
@RequestMapping("/api/customers/me")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;
  private final AuthService authService;


  @PostMapping
  public ResponseEntity<CustomerRequestDto> createCustomer(@RequestBody CustomerRequestDto dto) {
    CustomerRequestDto saved = customerService.createCustomer(dto);
    return ResponseEntity.ok(saved);
  }
  @SecurityRequirement(name="bearerAuth")
  @GetMapping
  public CustomerDetailDto getCustomerMe(@AuthenticationPrincipal CustomUserDetails user){
    System.out.println("AUTHORITIES=" + user.getAuthorities());
    System.out.println("USERNAME=" + user.getUsername());
    Long customerId = authService.getLoggedInCustomerId(user.getUsername());
    return customerService.getCustomersById(customerId);
  }

}


