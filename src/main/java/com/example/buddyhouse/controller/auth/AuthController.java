package com.example.buddyhouse.controller.auth;

import com.example.buddyhouse.dto.auth.AuthRequestDto;
import com.example.buddyhouse.dto.auth.SignupRequestDto;
import com.example.buddyhouse.dto.auth.TokenResponseDto;
import com.example.buddyhouse.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "01.Auth", description = "認証・ログイン")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody AuthRequestDto request) {
    TokenResponseDto response = authService.login(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
    authService.signup(request);
    return ResponseEntity.ok().build();
  }
}
