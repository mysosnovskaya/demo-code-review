package com.code.review.demo.storage;

public class LocalReceiptStorageService implements ReceiptStorageService {
    public String get(Long productId) {
        return "Local: " + productId;
    }
}

