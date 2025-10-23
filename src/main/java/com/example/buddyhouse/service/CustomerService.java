package com.example.buddyhouse.service;

import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
public CustomerEntity createCustomer(CustomerEntity customer){
  return customerRepository.save(customer);
}

}
