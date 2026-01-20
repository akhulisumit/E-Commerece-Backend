package com.example.ecommerce.controller;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/mock-payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> makePayment(
            @PathVariable String orderId,
            @RequestParam(defaultValue = "SUCCESS") String result) {


        Payment payment = paymentService.createMockPayment(orderId);

        PaymentWebhookRequest webhookRequest = new PaymentWebhookRequest();
        webhookRequest.setOrderId(orderId);
        webhookRequest.setPaymentId(UUID.randomUUID().toString());
        webhookRequest.setStatus(result);

        paymentService.processWebhook(webhookRequest);

        return ResponseEntity.ok(
                "Mock payment " + result + " for order " + orderId
        );
    }
}
