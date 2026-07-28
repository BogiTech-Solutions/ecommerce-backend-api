package com.ecommerce.service.impl;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("chapaPaymentService")
public class ChapaPaymentServiceImpl implements PaymentService {

    @Value("${payment.chapa.secret-key}")
    private String chapaSecretKey;

    @Value("${payment.chapa.base-url}")
    private String baseUrl;

    private final RestClient restClient;

    public ChapaPaymentServiceImpl() {
        this.restClient = RestClient.create();
    }

    @Override
    public PaymentResponse initializePayment(PaymentRequest request) {
        String txRef = "tx-order-" + request.getOrderId() + "-" + UUID.randomUUID().toString().substring(0, 8);

        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getAmount().toString());
        body.put("currency", request.getCurrency() != null ? request.getCurrency() : "ETB");
        body.put("email", request.getEmail());
        body.put("first_name", request.getFirstName());
        body.put("last_name", request.getLastName());
        body.put("tx_ref", txRef);
        body.put("callback_url", request.getCallbackUrl());
        body.put("return_url", request.getCallbackUrl() + "?tx_ref=" + txRef);

        try {
            Map<?, ?> response = restClient.post()
                    .uri(baseUrl + "/transaction/initialize")
                    .header("Authorization", "Bearer " + chapaSecretKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            if (response != null && "success".equals(response.get("status"))) {
                Map<?, ?> data = (Map<?, ?>) response.get("data");
                String checkoutUrl = (String) data.get("checkout_url");

                return PaymentResponse.builder()
                        .transactionRef(txRef)
                        .checkoutUrl(checkoutUrl)
                        .status("PENDING")
                        .build();
            } else {
                throw new BadRequestException("Chapa initialization failed.");
            }
        } catch (Exception e) {
            throw new BadRequestException("Chapa payment error: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyPayment(String transactionRef) {
        try {
            Map<?, ?> response = restClient.get()
                    .uri(baseUrl + "/transaction/verify/" + transactionRef)
                    .header("Authorization", "Bearer " + chapaSecretKey)
                    .retrieve()
                    .body(Map.class);

            return response != null && "success".equalsIgnoreCase((String) response.get("status"));
        } catch (Exception e) {
            return false;
        }
    }
}