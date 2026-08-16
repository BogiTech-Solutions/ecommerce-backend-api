package com.ecommerce.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdRequest {
  private String title;
  private String description;
  private MultipartFile image;
  private String targetUrl;
  private boolean active;
}
