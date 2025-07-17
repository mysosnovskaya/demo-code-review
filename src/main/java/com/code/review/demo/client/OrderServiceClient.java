package com.code.review.demo.client;

import org.springframework.web.client.RestTemplate;

public class OrderServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getOrderById(int orderId) {
        String url = "https://order-service/orders/" + orderId;
        return restTemplate.getForObject(url, String.class);
    }
}

