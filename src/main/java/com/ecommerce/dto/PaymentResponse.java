package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String transactionRef;
    private String checkoutUrl; // URL where user completes payment (Stripe Checkout / Chapa Hosted)
    private String status;
}