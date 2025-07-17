package com.code.review.demo.consumer;

import com.code.review.demo.client.OrderServiceClient;
import com.code.review.demo.entity.Order;
import com.code.review.demo.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderServiceClient client = new OrderServiceClient();
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created", groupId = "orders-group")
    public void consume(String payload) {
        System.out.println("Received: " + payload);

        if (payload.contains("fail")) {
            throw new RuntimeException("payload contains error");
        }

        String result = client.getOrderById(Integer.parseInt(payload));
        System.out.println("Result: " + result);
        paymentService.createPayment(objectMapper.convertValue(result, Order.class));
    }
}

