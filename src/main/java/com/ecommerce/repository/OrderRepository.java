package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserId(Long userId);

  @Query(
      value = "SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items",
      countQuery = "SELECT count(o) FROM Order o")
  Page<Order> findAllWithItems(Pageable pageable);
}
