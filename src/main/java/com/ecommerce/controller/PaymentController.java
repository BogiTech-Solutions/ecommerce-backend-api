package com.ecommerce.controller;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments", description = "Endpoints for Stripe and Chapa payment processing")
public class PaymentController {

    private final PaymentService stripePaymentService;
    private final PaymentService chapaPaymentService;

    public PaymentController(
            @Qualifier("stripePaymentService") PaymentService stripePaymentService,
            @Qualifier("chapaPaymentService") PaymentService chapaPaymentService) {
        this.stripePaymentService = stripePaymentService;
        this.chapaPaymentService = chapaPaymentService;
    }

    @PostMapping("/stripe/initialize")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Initialize Stripe Checkout session")
    public ResponseEntity<PaymentResponse> initStripe(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(stripePaymentService.initializePayment(request));
    }

    @PostMapping("/chapa/initialize")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Initialize Chapa payment transaction")
    public ResponseEntity<PaymentResponse> initChapa(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(chapaPaymentService.initializePayment(request));
    }

    @GetMapping("/verify/{gateway}/{txRef}")
    @Operation(summary = "Verify transaction status by provider")
    public ResponseEntity<Boolean> verifyPayment(
            @PathVariable String gateway, @PathVariable String txRef) {
        if ("chapa".equalsIgnoreCase(gateway)) {
            return ResponseEntity.ok(chapaPaymentService.verifyPayment(txRef));
        } else if ("stripe".equalsIgnoreCase(gateway)) {
            return ResponseEntity.ok(stripePaymentService.verifyPayment(txRef));
        }
        return ResponseEntity.badRequest().build();
    }
}
