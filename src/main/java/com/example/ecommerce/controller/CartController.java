package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.repository.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @PostMapping("/add")
    public CartItem add(@RequestBody AddToCartRequest req) {
        return service.add(req);
    }

    @GetMapping("/{userId}")
    public List<CartItem> get(@PathVariable String userId) {
        return service.getCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clear(@PathVariable String userId) {
        service.clear(userId);
        return "Cart cleared";
    }
}
