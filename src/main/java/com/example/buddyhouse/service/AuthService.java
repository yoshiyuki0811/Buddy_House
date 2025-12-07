package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.auth.LoginRequestDto;
import com.example.buddyhouse.dto.auth.SignupRequestDto;
import com.example.buddyhouse.dto.auth.TokenResponseDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.UserEntity;
import com.example.buddyhouse.mapper.CustomerMapper;
import com.example.buddyhouse.mapper.UserMapper;
import com.example.buddyhouse.repository.CustomerRepository;
import com.example.buddyhouse.repository.UserRepository;
import com.example.buddyhouse.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private final CustomerMapper customerMapper;
  private final UserMapper userMapper;


  @Transactional
  public void signup(SignupRequestDto dto) {
    // 1. email 重複チェック
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "このメールアドレスは既に登録されています。"
      );
    }

    // CustomerEntity 作成
    CustomerEntity customer = customerMapper.toEntity(dto);
    customerRepository.save(customer);

    //UserEntity 作成
    UserEntity user = userMapper.toEntity(dto, customer, passwordEncoder);

    userRepository.save(user);
  }

  public TokenResponseDto login(LoginRequestDto dto) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            dto.getEmail(),
            dto.getPassword()
        )
    );

    UserDetails user = (UserDetails) authentication.getPrincipal();
    String token = jwtService.generateToken(user);

    return TokenResponseDto.builder()
        .accessToken(token)
        .build();
  }
}
