package com.ecommerce.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stockQuantity;
  private MultipartFile image;
  private Long categoryId;
}
