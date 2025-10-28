package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.dto.CustomersListDto;
import com.example.buddyhouse.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
private  final CustomerService customerService;

@PostMapping
  public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto dto){
  CustomerDto saved = customerService.createCustomer(dto);
  return ResponseEntity.ok(saved);
}
@GetMapping
public ResponseEntity<List<CustomersListDto>> getCustomersList(){
  List<CustomersListDto> customers =customerService.getCustomersList();
  return ResponseEntity.ok(customers);
  }
  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getCustomersById(@PathVariable Long id){
  CustomerDto customer = customerService.getCustomersById(id);
  return ResponseEntity.ok(customer);
  }

}


