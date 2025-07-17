package com.code.review.demo.config;

import com.code.review.demo.storage.LocalReceiptStorageService;
import com.code.review.demo.storage.S3ReceiptStorageService;
import com.code.review.demo.storage.ReceiptStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiptStorageConfig {

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "local")
    public ReceiptStorageService s3StorageService() {
        return new S3ReceiptStorageService();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReceiptStorageService defaultStorage() {
        return new LocalReceiptStorageService();
    }
}

