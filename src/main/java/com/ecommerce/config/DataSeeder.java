package com.ecommerce.config;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) {
    seedAdminUser();
    seedCategoriesAndProducts();
  }

  private void seedAdminUser() {
    String adminEmail = "admin@ecommerce.com";

    if (userRepository.findByEmail(adminEmail).isPresent()) {
      log.info("Admin user already exists. Skipping admin seeding...");
      return;
    }

    log.info("Seeding default Admin user...");

    User admin =
        User.builder()
            .firstName("Admin")
            .lastName("User")
            .email(adminEmail)
            .password(passwordEncoder.encode("Admin123!"))
            .role(Role.ROLE_ADMIN)
            .enabled(true)
            .build();

    userRepository.save(admin);
    log.info("Admin user seeded successfully with email: {}", adminEmail);
  }

  private void seedCategoriesAndProducts() {
    if (categoryRepository.count() > 0) {
      log.info("Categories already seeded. Skipping product seeding...");
      return;
    }

    log.info("Seeding categories and products...");

    Category electronics =
        Category.builder().name("Electronics").description("Gadgets and devices").build();

    Category fashion =
        Category.builder().name("Fashion").description("Apparel and footwear").build();

    categoryRepository.saveAll(List.of(electronics, fashion));

    Product laptop =
        Product.builder()
            .name("ProBook Laptop 15\"")
            .description("High-performance laptop")
            .price(new BigDecimal("1299.99"))
            .stockQuantity(25)
            .category(electronics)
            .build();

    productRepository.save(laptop);
    log.info("Categories and products seeded successfully!");
  }
}
