package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public Order create(@RequestBody CreateOrderRequest req) {
        return service.create(req.getUserId());
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable String id) {
        return service.get(id);
    }
}
