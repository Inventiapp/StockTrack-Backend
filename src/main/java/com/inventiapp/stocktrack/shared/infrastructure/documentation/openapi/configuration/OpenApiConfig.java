package com.inventiapp.stocktrack.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .servers(List.of(
                new Server()
                    .url("https://stocktrack-backend-production.up.railway.app")
                    .description("Production Server"),
                new Server()
                    .url("http://localhost:8080")
                    .description("Local Development Server")
            ))
            .info(new Info()
                .title("StockTrack Backend API")
                .version("1.0.0")
                .description("Sistema de gestión de inventario"));
    }
}