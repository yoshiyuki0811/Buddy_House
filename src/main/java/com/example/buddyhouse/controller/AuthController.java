package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.LoginRequestDto;
import com.example.buddyhouse.dto.TokenResponseDto;
import com.example.buddyhouse.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto request) {

    //email / password で認証する
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    // 認証に成功したユーザー情報を取り出す
    UserDetails user = (UserDetails) authentication.getPrincipal();

    //JWT を発行
    String token = jwtService.generateToken(user);

    //レスポンスとしてトークンを返す
    TokenResponseDto response=TokenResponseDto.builder()
        .accessToken(token)
        .build();

    return ResponseEntity.ok(response);
  }


}
