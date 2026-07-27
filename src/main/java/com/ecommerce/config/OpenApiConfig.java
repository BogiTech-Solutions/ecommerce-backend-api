package com.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(contact = @Contact(name = "E-Commerce API Support", email = "support@ecommerce.com"), description = "OpenAPI documentation for E-Commerce Backend REST API", title = "E-Commerce API Specification", version = "1.0"), servers = {
        @Server(description = "Local Environment", url = "http://localhost:8080")
}, security = {
        @SecurityRequirement(name = "bearerAuth")
})
@SecurityScheme(name = "bearerAuth", description = "JWT authentication token", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}