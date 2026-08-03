package com.ecommerce.service;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse initializePayment(PaymentRequest request);

    boolean verifyPayment(String transactionRef);
}
