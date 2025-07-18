package com.code.review.demo.service;

import com.code.review.demo.entity.Order;
import com.code.review.demo.entity.OrderItem;
import com.code.review.demo.entity.Product;
import com.code.review.demo.entity.User;
import com.code.review.demo.repository.OrderRepository;
import com.code.review.demo.repository.ProductRepository;
import com.code.review.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public void placeOrder(Long userId, Map<String, Integer> productQuantities) {
        User user = userRepository.findById(userId).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findByName(entry.getKey());

            if (product.getStock() >= entry.getValue()) {
                product.setStock(product.getStock() - entry.getValue());
                OrderItem item = new OrderItem(null, order, product, entry.getValue());
                items.add(item);
            } else {
                log.info("Not enough stock for product {}", entry.getKey());
            }
        }

        order.setItems(items);
        orderRepository.save(order);
        log.info("Order placed with {} items", items.size());
    }

    public List<Product> getTopProducts() {
        List<Product> allProducts = productRepository.findAll();
        allProducts.sort((p1, p2) -> {
            if (p1.getStock() > p2.getStock()) return -1;
            else if (p1.getStock() < p2.getStock()) return 1;
            else return 0;
        });
        return allProducts.subList(0, 5);
    }
}

