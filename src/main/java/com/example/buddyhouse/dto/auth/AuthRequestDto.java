package com.example.buddyhouse.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthRequestDto {
  @Schema(description = "メールアドレス", example = "test@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;
  @Schema(description = "パスワード", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;
}
