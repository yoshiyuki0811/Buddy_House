package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.customer.CustomerDetailDto;
import com.example.buddyhouse.dto.customer.CustomerDto;
import com.example.buddyhouse.dto.customer.CustomersListDto;
import com.example.buddyhouse.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/customers")
@RequiredArgsConstructor
public class AdminCustomerController {

  private final CustomerService customerService;

  @GetMapping
  public List<CustomersListDto> getCustomersList() {
    return customerService.getCustomersList();
  }

  @GetMapping("/{id}")
  public CustomerDetailDto getCustomersById(@PathVariable Long id) {
    return customerService.getCustomersById(id);
  }

  @PatchMapping("/{id}/delete")
  public ResponseEntity<Void> deletedCustomersById(@PathVariable Long id) {
    customerService.deleteCustomerById(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CustomerDto> updateCustomersById(@PathVariable Long id, @RequestBody CustomerDto dto) {
    CustomerDto update = customerService.updateCustomerById(id, dto);
    return ResponseEntity.ok(update);
  }

}
