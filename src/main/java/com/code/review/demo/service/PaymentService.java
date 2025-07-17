package com.code.review.demo.service;

import com.code.review.demo.entity.Order;
import com.code.review.demo.entity.Payment;
import com.code.review.demo.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final double ORDER_PRICE = 10_000;
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void createPayment(Order order) {
        Payment payment =
                paymentRepository.save(new Payment(null, order.getId(), ORDER_PRICE, order.getCustomer()));
        log.info("payment {} for order {} created", payment.getId(), order.getId());
        processPayment(payment);
    }

    @Async
    public void processPayment(Payment payment) {
        kafkaTemplate.send("payment-created", payment);
        kafkaTemplate.send("order-processing", payment.getOrderId());
    }
}
