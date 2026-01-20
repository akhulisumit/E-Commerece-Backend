package com.example.ecommerce.repository;

import com.example.ecommerce.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OrderRepository extends MongoRepository<Order, String> {}