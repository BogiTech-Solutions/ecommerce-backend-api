package com.ecommerce.dto.user;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.UserStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private String avator;
  private String phone;
  private Role role;
  private UserStatus status;
  private BigDecimal totalSpent;
  private Long totalOrders;
  private boolean enabled;
  private LocalDateTime createdAt;
}
