package com.example.ecommerce.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
public class Payment {
    @Id
    private String id;
    private String orderId;
    private Double amount;
    private String status;
    private Instant createdAt;
}
