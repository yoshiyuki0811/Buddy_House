package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.repository.CustomerRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {


  private final CustomerRepository customerRepository;

public CustomerDto createCustomer(CustomerDto dto){
  //DTOからEntityへ変換
  CustomerEntity entity = new CustomerEntity();
  entity.setName(dto.getName());
  entity.setAddress(dto.getAddress());
  entity.setEmail(dto.getEmail());
  entity.setPhone(dto.getPhone());
  entity.setCreatedAt(LocalDateTime.now());
  entity.setUpdatedAt(LocalDateTime.now());
//DBへ保存
 CustomerEntity saved =customerRepository.save(entity);
 //EntityからDTOへ変換してreturn
 return new CustomerDto(saved);



}

}



