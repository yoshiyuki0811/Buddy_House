package com.example.buddyhouse.security;

import com.example.buddyhouse.entity.UserEntity;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

  private final UserEntity user;


  // ログインIDとして email を使う
  @Override
  public String getUsername() {
    return user.getEmail();
  }

  // ハッシュ済みパスワード
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  // 権限（ROLE_ADMIN / ROLE_USER）
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    String roleName = "ROLE_" + user.getRole().name();
    return List.of(new SimpleGrantedAuthority(roleName));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // アカウント期限は管理しないので true 固定
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // ロック機能まだなし
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // パスワード期限管理もしない
  }

  @Override
  public boolean isEnabled() {
    return user.isEnabled();
  }
}
