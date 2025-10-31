package com.example.buddyhouse.service;


import com.example.buddyhouse.dto.CustomerDto;
import com.example.buddyhouse.dto.CustomersListDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.mapper.CustomerMapper;
import com.example.buddyhouse.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerDto createCustomer(CustomerDto dto) {
    CustomerEntity entity = customerMapper.toEntity(dto);
    CustomerEntity saved = customerRepository.save(entity);// DB保存
    return customerMapper.toDto(saved);
  }

public List<CustomersListDto> getCustomersList(){
    return customerRepository.findAllForList();
  }
  public CustomerDto getCustomersById(Long id){
    CustomerEntity entity = customerRepository.findById(id)
        .orElseThrow(()->new RuntimeException("こきゃくがみつかりません。"));
return customerMapper.toDto(entity);
  }
@Transactional
  public CustomerDto deleteCustomerById(Long id){
    CustomerEntity entity = customerRepository.findById(id)
        .orElseThrow(()->new RuntimeException("こきゃくがみつかりません。"));
    entity.setDeleted(true);
    CustomerEntity updated =customerRepository.save(entity);

    return customerMapper.toDto(updated);
  }


}





