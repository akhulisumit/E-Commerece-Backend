package com.example.ecommerce.webhook;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/webhooks")
public class PaymentWebhookController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    // Endpoint to receive payment webhook (mock or real)
    @PostMapping("/payment")
    public ResponseEntity<?> handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        try {
            if (request.getOrderId() == null || request.getStatus() == null) {
                throw new RuntimeException("Invalid request body");
            }

            // 1️⃣ Update payment status
            paymentService.updatePaymentStatus(request.getOrderId(), request.getStatus());

            // 2️⃣ Update order status based on payment result
            String orderStatus = "SUCCESS".equalsIgnoreCase(request.getStatus()) ? "PAID" : "FAILED";
            orderService.updateOrderStatus(request.getOrderId(), orderStatus);

            // 3️⃣ Response
            Map<String, String> response = new HashMap<>();
            response.put("message", "Webhook processed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
