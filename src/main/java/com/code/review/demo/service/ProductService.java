package com.code.review.demo.service;

import com.code.review.demo.model.Product;
import com.code.review.demo.repository.ProductRepository;
import com.code.review.demo.storage.ReceiptStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int TOP_PRODUCTS = 100;

    private final ProductRepository productRepository;
    private final ReceiptStorageService receiptStorageService;

    @Cacheable("products")
    public List<Product> getPopularProducts() {
        List<Product> products = productRepository.findTopProducts(TOP_PRODUCTS);
        products.stream().map(Product::getId).forEach(this::increaseViews);
        products.forEach(product -> product.setReceipt(receiptStorageService.get(product.getId())));
        return products;
    }

    public void addProduct(Product p) {
        productRepository.save(p);
    }

    @Async
    @Transactional
    public void increaseViews(Long productId) {
        Product p = productRepository.findById(productId).orElseThrow();
        p.setViews(p.getViews() + 1);
        productRepository.save(p);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

