package com.code.review.demo.service;

import com.code.review.demo.entity.Role;
import com.code.review.demo.entity.User;
import com.code.review.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<String> findAll() {
        List<User> users =  repository.findAll();
        List<String> result = new ArrayList<>();
        for (User u : users) {
            if ("active".equals(u.getStatus())) {
                for (Role r : u.getRoles()) {
                result.add(u.getName() + ": " + r.getName());
                }
            }
        }
        return result;
    }

    public void save(Map<String, Object> body) {
        String name = (String) body.get("name");
        String email = (String) body.get("email");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setStatus("active");
        repository.save(user);
    }
}

