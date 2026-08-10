package com.ecommerce.repository;

import com.ecommerce.entity.Ad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
  // Custom query to fetch only active ads for clients
  List<Ad> findByActiveTrue();
}
