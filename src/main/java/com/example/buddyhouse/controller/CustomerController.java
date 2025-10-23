package com.example.buddyhouse.controller;

import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer){
  CustomerEntity saveCustomer = customerService.createCustomer(customer);
  return ResponseEntity.ok(saveCustomer);
}

}
