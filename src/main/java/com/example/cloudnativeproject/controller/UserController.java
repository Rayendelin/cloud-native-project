package com.example.cloudnativeproject.controller;

import com.example.cloudnativeproject.limit.MyLimit;
import com.example.cloudnativeproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/greeting")
    @MyLimit(key = "limit")
    public String greeting() {
        return userService.greeting();
    }
}