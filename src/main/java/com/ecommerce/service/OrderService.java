package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.OrderStatus;
import java.util.List;

public interface OrderService {
  OrderResponse createOrder(String userEmail, OrderRequest request);

  List<OrderResponse> getUserOrders(String userEmail);

  OrderResponse getOrderById(Long orderId, String userEmail);

  OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
}
