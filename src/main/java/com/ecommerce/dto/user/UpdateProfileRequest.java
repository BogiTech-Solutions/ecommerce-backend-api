package com.ecommerce.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileRequest {
  private String firstName;
  private String lastName;
  private MultipartFile avatar;
  private String phone;
  private String bio;
}
