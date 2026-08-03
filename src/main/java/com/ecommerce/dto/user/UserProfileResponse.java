package com.ecommerce.dto.user;

import com.ecommerce.entity.Role;
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
  private Role role;
  private boolean enabled;
  private LocalDateTime createdAt;
}
