package com.amazon.orderservice.model;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private long id;
    private long productId;
    private long quantity;
    @JsonDeserialize(as = LocalDate.class)
    private LocalDate paymentDate;
    @JsonDeserialize(as = LocalDate.class)
    private LocalDate orderDate;
    private String orderStatus;
    private long amount;
    private String paymentMode;
    private ProductResponse productResponse;
    private PaymentResponse paymentResponse;
    

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductResponse {
        private long productId;
        private String productName;
        private long price;
        private long quantity;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class PaymentResponse {
        private long id;
        private String paymentMode;
        private String referenceNumber;
        private Instant paymentDate;
        private String paymentStatus;
        private long amount;
    }
}
