package com.eDiya.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myCustomOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info().title("Journal App API")
                                .version("1.0")
                                .description("API documentation for Journal App")
                )
                .servers(Arrays.asList(new Server().url("http://localhost:8080/journal").description("Local server"),
                        new Server().url("http://localhost:8081/journal").description("Production server")))
                .tags(Arrays.asList(
                        new Tag().name("Public APIs").description("Endpoints accessible without authentication"),
                        new Tag().name("User APIs").description("Endpoints for user operations"),
                        new Tag().name("Admin APIs").description("Endpoints for admin operations"),
                        new Tag().name("Journal APIs").description("Endpoints for journal entry operations")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
