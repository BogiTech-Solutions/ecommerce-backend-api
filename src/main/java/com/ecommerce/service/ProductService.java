package com.ecommerce.service;

import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import java.util.List;
import org.jspecify.annotations.Nullable;

public interface ProductService {
  ProductResponse createProduct(ProductRequest request);

  List<ProductResponse> getAllProducts();

  PageResponse<ProductResponse> getPaginatedProducts(
      int page, int size, String sortBy, String sortDir);

  ProductResponse getProductById(Long id);

  List<ProductResponse> getProductsByCategory(Long categoryId);

  ProductResponse updateProduct(Long id, ProductRequest request);

  void deleteProduct(Long id);

  @Nullable Object searchProducts(String query);

  @Nullable Object getPopularProducts();

  @Nullable Object getProductsForYou();
}
