package com.example.buddyhouse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
  }
}

