package com.example.ecommerce.service;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    public Payment createMockPayment(String orderId) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setStatus("PENDING");
        payment.setCreatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    public void updatePaymentStatus(String orderId, String status) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        paymentRepository.save(payment);
    }

    public void processWebhook(PaymentWebhookRequest request) {
        updatePaymentStatus(request.getOrderId(), request.getStatus());

        String orderStatus = "SUCCESS".equals(request.getStatus()) ? "PAID" : "FAILED";
        orderService.updateOrderStatus(request.getOrderId(), orderStatus);
    }
}
