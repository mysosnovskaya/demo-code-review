package com.code.review.demo.entity;

import lombok.Data;

@Data
public class Order {
    private Long id;

    private String name;

    private String status;

    private String customer;
}

