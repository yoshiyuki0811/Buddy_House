package com.example.buddyhouse.service;


import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.mapper.CustomerMapper;
import com.example.buddyhouse.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerService(CustomerRepository customerRepository,
      CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public CustomerDto createCustomer(CustomerDto dto) {
    // DTO → Entity
    CustomerEntity entity = customerMapper.createEntity(dto);

    // DB保存
    CustomerEntity saved = customerRepository.save(entity);

    // Entity → DTO
    return customerMapper.createDto(saved);
  }



}





