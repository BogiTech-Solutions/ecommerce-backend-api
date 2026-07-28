package com.ecommerce.config;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile({"dev", "local", "default"})
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Skip seeding if data already exists
        if (categoryRepository.count() > 0) {
            log.info("Database already seeded with categories and products. Skipping...");
            return;
        }

        log.info("Seeding categories and products into database...");

        // 1. Create Categories
        Category electronics = Category.builder()
                .name("Electronics")
                .description("Gadgets, devices, and electronic accessories")
                .build();

        Category fashion = Category.builder()
                .name("Fashion")
                .description("Apparel, footwear, and accessories")
                .build();

        Category homeGoods = Category.builder()
                .name("Home & Living")
                .description("Furniture, home decor, and appliances")
                .build();

        categoryRepository.saveAll(List.of(electronics, fashion, homeGoods));

        // 2. Create Products associated with saved Categories
        Product laptop = Product.builder()
                .name("ProBook Laptop 15\"")
                .description("High-performance laptop with 16GB RAM and 512GB SSD")
                .price(new BigDecimal("1299.99"))
                .stockQuantity(25)
                .imageUrl("http://localhost:8080/uploads/sample-laptop.jpg")
                .category(electronics)
                .build();

        Product wirelessMouse = Product.builder()
                .name("Ergonomic Wireless Mouse")
                .description("2.4GHz silent optical mouse with adjustable DPI")
                .price(new BigDecimal("29.99"))
                .stockQuantity(100)
                .imageUrl("http://localhost:8080/uploads/sample-mouse.jpg")
                .category(electronics)
                .build();

        Product leatherJacket = Product.builder()
                .name("Classic Leather Jacket")
                .description("100% genuine leather jacket in dark brown")
                .price(new BigDecimal("189.50"))
                .stockQuantity(15)
                .imageUrl("http://localhost:8080/uploads/sample-jacket.jpg")
                .category(fashion)
                .build();

        Product coffeeMaker = Product.builder()
                .name("Automatic Espresso Machine")
                .description("15-bar pump pressure espresso maker with milk frother")
                .price(new BigDecimal("249.00"))
                .stockQuantity(10)
                .imageUrl("http://localhost:8080/uploads/sample-espresso.jpg")
                .category(homeGoods)
                .build();

        productRepository.saveAll(List.of(laptop, wirelessMouse, leatherJacket, coffeeMaker));

        log.info("Successfully seeded 3 categories and 4 products!");
    }
}