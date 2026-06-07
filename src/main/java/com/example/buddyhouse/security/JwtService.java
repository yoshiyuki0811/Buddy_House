package com.example.buddyhouse.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  private final String secret;
  private final long accessTokenValidityMs;

  private SecretKey signingKey;

  public JwtService(
      @Value("${security.jwt.secret}") String secret,
      @Value("${security.jwt.expire-ms}") long accessTokenValidityMs
  ) {
    this.secret = secret;
    this.accessTokenValidityMs = accessTokenValidityMs;
  }

  @PostConstruct
  void initKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.signingKey = Keys.hmacShaKeyFor(keyBytes);
  }

  // トークン発行
  public String generateToken(UserDetails userDetails) {
    Instant now = Instant.now();
    Instant expiry = now.plusMillis(accessTokenValidityMs);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(a -> a.getAuthority())
        .toList();

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim("roles", roles)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiry))
        .signWith(signingKey)
        .compact();
  }

  // username 取り出し
  public String extractUsername(String token) {
    return getClaims(token).getSubject();
  }

  // トークン有効性チェック
  public boolean isTokenValid(String token, UserDetails userDetails) {
    Claims claims = getClaims(token);
    String username = claims.getSubject();
    Date expiration = claims.getExpiration();

    return userDetails.getUsername().equals(username)
        && expiration.after(new Date());
  }

  // 内部ヘルパー（ここがパース処理の本丸）
  private Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
