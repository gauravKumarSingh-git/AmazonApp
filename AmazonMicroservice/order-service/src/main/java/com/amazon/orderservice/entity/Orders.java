package com.amazon.orderservice.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
