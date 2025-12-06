package com.example.buddyhouse.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    // 1. AuthorizationヘッダからBearerトークンを取り出す
    String authHeader = request.getHeader("Authorization");

    String jwt = null;
    String username = null;

    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7); // "Bearer " を除去
      try {
        // 2. トークンからusername（subject）を取り出す
        username = jwtService.extractUsername(jwt);
      } catch (Exception ex) {
        // トークン不正・期限切れなど → 認証なしのまま次へ進める
        // ここでレスポンス返さないのがポイント（ただの匿名扱いにする）
      }
    }

    // 3. まだ認証されておらず、usernameが取れている場合だけ認証処理を行う
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // 3-1. DB or UserDetailsService からユーザー情報を取得
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      // 3-2. トークンが有効かチェック
      if (jwtService.isTokenValid(jwt, userDetails)) {

        // 3-3. Spring Security 用の認証オブジェクトを作成
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );

        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // 3-4. SecurityContext にセット → 以降の処理で「認証済みユーザー」として扱われる
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    // 4. 次のフィルター / コントローラーへ処理を進める
    filterChain.doFilter(request, response);
  }
}
