package com.example.buddyhouse.mapper;

import com.example.buddyhouse.dto.auth.SignupRequestDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.UserEntity;
import com.example.buddyhouse.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserEntity toEntity(SignupRequestDto dto, CustomerEntity customer, PasswordEncoder encoder) {
    return UserEntity.builder()
        .email(dto.getEmail())
        .password(encoder.encode(dto.getPassword()))
        .role(UserRole.CUSTOMER)
        .customer(customer)
        .enabled(true)
        .build();
  }
}


