package com.example.buddyhouse.config;

import com.example.buddyhouse.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//セキュリティールールは基本的に全部ここに書く
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomUserDetailsService customUserDetailsService;

  /**
   * 認可・認証のルール本体。
   * 「どのURLを誰に開けるか」をここで全部決める。
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        // ★CSRFトークンは今回はOFF
        //   → ブラウザからフォームPOSTするわけではないAPIだから基本不要
        .csrf(AbstractHttpConfigurer::disable)

        // ★セッションを使わない(= 毎回認証情報を送るスタイル)
        //   Basic認証やJWT前提のAPIではこれがほぼテンプレ
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // ★URLごとのアクセスルール
        .authorizeHttpRequests(auth -> auth
            // Swagger や API ドキュメントは誰でも見れるようにする
            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/actuator/health"
            ).permitAll()
            // 認証不要
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/menus/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/reservation-frames/available/**").permitAll()

            //ADMINのみOK
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            // 顧客のマイページ（会員）
            .requestMatchers("/api/customers/me/**").hasRole("CUSTOMER")


            //上記以外はログインしてさえいればOK
            .anyRequest().authenticated()
        )

        .httpBasic(Customizer.withDefaults())
        .userDetailsService(customUserDetailsService);


    // ★最後にビルドしてFilterChainとして返す
    return http.build();
  }
  /**
   * パスワードのハッシュ化に使うエンコーダ。
   * 実務でもほぼこれ一択。テンプレ。
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
