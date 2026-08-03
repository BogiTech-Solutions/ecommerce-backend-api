package com.ecommerce.dto.user;

import com.ecommerce.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {
  @NotNull(message = "Role is required")
  private Role role;
}
