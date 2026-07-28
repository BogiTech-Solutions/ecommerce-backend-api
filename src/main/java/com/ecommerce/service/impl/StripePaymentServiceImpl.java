package com.ecommerce.service.impl;

import com.ecommerce.dto.PaymentRequest;
import com.ecommerce.dto.PaymentResponse;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("stripePaymentService")
public class StripePaymentServiceImpl implements PaymentService {

    @Value("${payment.stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentResponse initializePayment(PaymentRequest request) {
        try {
            // Stripe expects amount in cents (e.g., $10.00 -> 1000)
            long amountInCents = request.getAmount().multiply(BigDecimal.valueOf(100)).longValue();

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setCustomerEmail(request.getEmail())
                    .setSuccessUrl(request.getCallbackUrl() + "?session_id={CHECKOUT_SESSION_ID}&orderId=" + request.getOrderId())
                    .setCancelUrl(request.getCallbackUrl() + "?canceled=true")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(request.getCurrency().toLowerCase())
                                                    .setUnitAmount(amountInCents)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Order #" + request.getOrderId())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            return PaymentResponse.builder()
                    .transactionRef(session.getId())
                    .checkoutUrl(session.getUrl())
                    .status("PENDING")
                    .build();

        } catch (StripeException e) {
            throw new BadRequestException("Stripe initialization failed: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyPayment(String transactionRef) {
        try {
            Session session = Session.retrieve(transactionRef);
            return "paid".equalsIgnoreCase(session.getPaymentStatus());
        } catch (StripeException e) {
            return false;
        }
    }
}