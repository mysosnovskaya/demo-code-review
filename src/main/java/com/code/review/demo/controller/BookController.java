package com.code.review.demo.controller;

import com.code.review.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping("/{id}")
    public void runTest(@PathVariable long id) {
        service.getById(id);
    }

    @PostMapping
    public void runTest() {
        service.create();
    }
}
