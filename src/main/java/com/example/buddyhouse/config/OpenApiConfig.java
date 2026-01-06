package com.example.buddyhouse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Primary;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        // ① 見た目（タイトル・説明・バージョン）
        .info(new Info()
            .title("Buddy House API")
            .description("""
                ペットホテル向けWeb予約管理アプリケーションの REST API です。
                JWT 認証を採用し、ADMIN / CUSTOMER のロールにより操作可能なAPIが異なります。
                """)
            .version("1.0.0"))

        .tags(List.of(
            new Tag().name("01.Auth").description("認証・ログイン"),
            new Tag().name("02.Customers").description("顧客関連操作(会員)"),
            new Tag().name("03.Admin　-　Customers").description("顧客管理(管理者)"),
            new Tag().name("04.Pets").description("ペット関連操作(会員)"),
            new Tag().name("05.Admin　-　Pets").description("ペット管理(管理者)"),
            new Tag().name("06.Reservations").description("予約操作(会員)"),
            new Tag().name("07.Admin　-　Reservations").description("予約管理(管理者)"),
            new Tag().name("08.ReservationFrames").description("予約枠参照(会員)"),
            new Tag().name("09.Admin　-　ReservationFrame").description("予約枠管理(管理者)"),
            new Tag().name("10.Admin　-　Menus").description("メニュー管理(管理者)")
        ))

        // ② サーバURL（必要なら）
        .servers(List.of(
            new Server().url("http://localhost:8080").description("Local"),
            new Server().url("https://buddy-house-app.com").description("Production")
        ))

                // ③ Authorize（JWT）
        .components(new Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));

  }

}

