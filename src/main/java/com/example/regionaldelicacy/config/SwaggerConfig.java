package com.example.regionaldelicacy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${APP_URL}")
    private String appUrl;

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url(appUrl))
                .info(new Info()
                        .title("Regional Delicacy Shop API")
                        .version("1.0")
                        .description("API documentation for Regional Delicacy Shop"))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
