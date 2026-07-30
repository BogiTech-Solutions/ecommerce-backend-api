package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. Allow credentials (cookies, authorization headers)
        config.setAllowCredentials(true);

        // 2. Allowed Frontend Origins (Update as needed for production)
        config.setAllowedOriginPatterns(List.of(
                "https://ecommerce-admin-two-phi.vercel.app",
                "http://localhost:3000", // React / Next.js
                "http://localhost:5173", // Vite / Vue / React
                "http://localhost:4200", // Angular
                "http://127.0.0.1:*"));

        // 3. Allowed HTTP Headers
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Cache-Control",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin"));

        // 4. Allowed HTTP Methods
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 5. Expose Headers (Required if your frontend reads custom headers or JWT
        // tokens directly)
        config.setExposedHeaders(List.of("Authorization", "Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}