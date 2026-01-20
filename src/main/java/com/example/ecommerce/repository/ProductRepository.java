package com.example.ecommerce.repository;

import com.example.ecommerce.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}