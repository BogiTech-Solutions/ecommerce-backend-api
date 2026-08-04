package com.ecommerce.controller;

import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for managing inventory products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/page")
  @Operation(
      summary = "Get paginated products",
      description = "Public endpoint to fetch products with pagination and sorting")
  public ResponseEntity<PageResponse<ProductResponse>> getPaginatedProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {
    return ResponseEntity.ok(productService.getPaginatedProducts(page, size, sortBy, sortDir));
  }

  @GetMapping("/for-you")
  @Operation(
      summary = "Get products for you",
      description = "Public endpoint to fetch products recommended for the user")
  public ResponseEntity<@Nullable Object> getProductsForYou() {
    return ResponseEntity.ok(productService.getProductsForYou());
  }

  @GetMapping("/popular")
  @Operation(
      summary = "Get popular products",
      description = "Public endpoint to fetch popular products")
  public ResponseEntity<@Nullable Object> getPopularProducts() {
    return ResponseEntity.ok(productService.getPopularProducts());
  }

  @GetMapping("/search")
  @Operation(
      summary = "Search products by name or description",
      description = "Public endpoint to search products based on name or description")
  public ResponseEntity<@Nullable Object> searchProducts(@RequestParam String query) {
    return ResponseEntity.ok(productService.searchProducts(query));
  }

  @GetMapping
  @Operation(
      summary = "Get all products",
      description = "Public endpoint to list all available products")
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get product by ID")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @GetMapping("/category/{categoryId}")
  @Operation(summary = "Get products by Category ID")
  public ResponseEntity<List<ProductResponse>> getProductsByCategory(
      @PathVariable Long categoryId) {
    return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
  }

  @PostMapping
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Create a new product", description = "Requires JWT authentication")
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
    return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Update product details")
  public ResponseEntity<ProductResponse> updateProduct(
      @PathVariable Long id, @RequestBody ProductRequest request) {
    return ResponseEntity.ok(productService.updateProduct(id, request));
  }

  @DeleteMapping("/{id}")
  @SecurityRequirement(name = "bearerAuth")
  @Operation(summary = "Delete a product")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
