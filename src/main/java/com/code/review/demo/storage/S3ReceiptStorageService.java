package com.code.review.demo.storage;

public class S3ReceiptStorageService implements ReceiptStorageService {
    public String get(Long productId) {
        return "S3: " + productId;
    }
}

