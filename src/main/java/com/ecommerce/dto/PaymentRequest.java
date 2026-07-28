package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private String currency; // "USD" for Stripe, "ETB" for Chapa
    private String email;
    private String firstName;
    private String lastName;
    private String callbackUrl;
}