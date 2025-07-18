package com.code.review.demo.repository;

import com.code.review.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
        SELECT * FROM products WHERE active = true
        ORDER BY views DESC LIMIT ?1
        """, nativeQuery = true)
    List<Product> findTopProducts(int limit);
}