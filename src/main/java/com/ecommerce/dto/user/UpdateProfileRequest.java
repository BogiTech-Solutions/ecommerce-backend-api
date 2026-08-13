package com.ecommerce.dto.user;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.UserStatus;
import java.math.BigDecimal;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileRequest {
  private String firstName;
  private String lastName;
  private MultipartFile avator;
  private String phone;
  private Role role;
  private UserStatus status;
  private BigDecimal totalSpent;
  private Long totalOrders;
}
