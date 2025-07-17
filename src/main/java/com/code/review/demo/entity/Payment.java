package com.code.review.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@Getter
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private long orderId;

    private double sum;

    private String customer;
}
