package com.e_bank.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration de Swagger pour l'API d'authentification.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configure l'objet OpenAPI avec les informations de l'API et les exigences de sécurité.
     *
     * @return l'objet OpenAPI configuré.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(apiInfo());
    }

    /**
     * Crée un schéma de sécurité API pour l'authentification par jeton Bearer.
     *
     * @return le schéma de sécurité configuré.
     */
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    /**
     * Configure les informations de l'API.
     *
     * @return l'objet Info configuré.
     */
    private Info apiInfo() {
        return new Info()
                .title("Authentication Service API Documentation")
                .version("1.0.0")
                .description("HTTP APIs to manage user registration and authentication.")
                .contact(new Contact().name("Yassine OULARBI"));
    }
}
