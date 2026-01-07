package com.example.buddyhouse.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "会員登録リクエスト")
public class SignupRequestDto {
  @Schema(description = "メールアドレス（ログインIDとして使用）", example = "buddy.house@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  @Email
  String email;

  @Schema(description = "パスワード（8文字以上）", example = "password1234", minLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  @Size(min = 8)
  String password;

  @Schema(description = "氏名", example = "山田 太郎", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank
  String name;

  // 任意
  @Schema(description = "電話番号（ハイフンなし）", example = "09012345678")
  String phone;

  @Schema(description = "住所", example = "東京都板橋区1-2-3")
  String address;

}