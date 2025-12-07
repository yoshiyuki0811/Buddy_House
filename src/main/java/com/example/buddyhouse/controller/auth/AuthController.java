package com.example.buddyhouse.controller.auth;

import com.example.buddyhouse.dto.auth.LoginRequestDto;
import com.example.buddyhouse.dto.auth.SignupRequestDto;
import com.example.buddyhouse.dto.auth.TokenResponseDto;
import com.example.buddyhouse.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request) {
    TokenResponseDto response = authService.login(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@RequestBody SignupRequestDto request) {
    authService.signup(request);
    return ResponseEntity.ok().build();
  }
}
