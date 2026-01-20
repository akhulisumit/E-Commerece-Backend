package com.example.ecommerce.service;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public Order create(String userId) {
        List<CartItem> cart = cartRepo.findByUserId(userId);
        double total = 0;

        for (CartItem c : cart) {
            Product p = productRepo.findById(c.getProductId()).orElseThrow();
            total += p.getPrice() * c.getQuantity();
            p.setStock(p.getStock() - c.getQuantity());
            productRepo.save(p);
        }

        Order o = new Order();
        o.setUserId(userId);
        o.setTotalAmount(total);
        o.setStatus("CREATED");
        o.setCreatedAt(Instant.now());

        cartRepo.deleteByUserId(userId);
        return orderRepo.save(o);
    }

    public Order get(String id) {
        return orderRepo.findById(id).orElseThrow();
    }

    public void updateOrderStatus(String orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepo.save(order);
    }

}
