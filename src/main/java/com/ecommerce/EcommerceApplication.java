package com.ecommerce;

import com.ecommerce.config.EnvConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigurationProperties(EnvConfig.class) // Enable our central config class
public class EcommerceApplication {
    public static void main(String[] args) {
        // 1. Load .env file (ignores if missing, like in Docker/Prod)
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // 2. Export entries into Java System Properties
        dotenv.entries()
                .forEach(
                        entry -> {
                            // Only set if not already set by OS environment
                            if (System.getProperty(entry.getKey()) == null) {
                                System.setProperty(entry.getKey(), entry.getValue());
                            }
                        });

        // 3. Launch Spring Boot
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Bean
    CommandLineRunner testEnv(Environment env) {
        return args -> {
            System.out.println("=================================================");
            System.out.println(
                    "STRIPE_SECRET_KEY direct env lookup: " + env.getProperty("STRIPE_SECRET_KEY"));
            System.out.println(
                    "app.payment.stripe.secret-key YAML lookup: "
                            + env.getProperty("app.payment.stripe.secret-key"));
            System.out.println("=================================================");
        };
    }
}
