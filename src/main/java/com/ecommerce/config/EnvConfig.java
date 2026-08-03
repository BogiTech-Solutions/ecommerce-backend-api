package com.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record EnvConfig(Security security, Payment payment, File file) {
    public record Security(Jwt jwt) {
        public record Jwt(String secret, long expiration) {}
    }

    public record Payment(Stripe stripe, Chapa chapa) {
        public record Stripe(String secretKey) {}

        public record Chapa(String secretKey) {}
    }

    public record File(String uploadDir) {}

    public String getSigningKey() {
        return security().jwt().secret();
    }
}
