package com.code.review.demo.controller;

import com.code.review.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/active")
    public List<String> getActiveUsers() {
        return service.findAll();
    }
    @PostMapping("/create")
    public void create(@RequestBody Map<String, Object> body) {
        service.save(body);
    }
}
