package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategoryId(Long categoryId);

  Streamable<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
      String query, String query2);
}
