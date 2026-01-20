package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository repo;

    public CartItem add(AddToCartRequest req) {
        CartItem item = new CartItem();
        item.setUserId(req.getUserId());
        item.setProductId(req.getProductId());
        item.setQuantity(req.getQuantity());
        return repo.save(item);
    }

    public List<CartItem> getCart(String userId) {
        return repo.findByUserId(userId);
    }

    public void clear(String userId) {
        repo.deleteByUserId(userId);
    }
}
