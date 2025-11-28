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

  //顧客の登録
  public CustomerDto createCustomer(CustomerDto dto) {
    CustomerEntity entity = customerMapper.toEntity(dto);
    CustomerEntity saved = customerRepository.save(entity);// DB保存
    return customerMapper.toDto(saved);
  }

  //顧客の一覧を取得
  public List<CustomersListDto> getCustomersList() {
    List<CustomerEntity> entity = customerRepository.findAll();
    return entity.stream()
        .map(customerMapper::toListDto)//Entityから取得した情報をMapperにいれてDto化
        .toList();//DTOの形になった顧客情報
  }

  //特定の顧客の詳細取得
  public CustomerDto getCustomersById(Long id) {
    CustomerEntity entity = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("顧客ががみつかりません。"));
    return customerMapper.toDto(entity);
  }

  //顧客情報の論理削除変更して保存
  @Transactional
  public void deleteCustomerById(Long id) {
    CustomerEntity entity = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("顧客がみつかりません。"));
    entity.setDeleted(true);
    customerRepository.save(entity);
  }


  //顧客情報の編集　変更があった場合Dtoのセット
  public CustomerDto updateCustomerById(Long id, CustomerDto customerDto) {
    CustomerEntity entity = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("顧客がみつかりません。"));

    if (customerDto.getName() != null) {
      entity.setName(customerDto.getName());
    }
    if (customerDto.getAddress() != null) {
      entity.setAddress(customerDto.getAddress());
    }
    if (customerDto.getEmail() != null) {
      entity.setEmail(customerDto.getEmail());
    }
    if (customerDto.getPhone() != null) {
      entity.setPhone(customerDto.getPhone());
    }

    CustomerEntity updated = customerRepository.save(entity);
    return customerMapper.toDto(updated);
  }
}






