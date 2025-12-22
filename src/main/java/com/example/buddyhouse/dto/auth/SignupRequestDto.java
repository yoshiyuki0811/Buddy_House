package com.example.buddyhouse.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {
  // ===== User用 =====
  @NotBlank
  @Email
  String email;

  @NotBlank
  @Size(min = 8)
  String password;

  // ===== Customer用 =====
  @NotBlank
  String name;

  // 任意
  String phone;
  String address;

}
