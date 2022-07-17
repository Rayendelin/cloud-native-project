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
    @MyLimit(key = "limit", permitPerSecond = 2,time = 1)
    public String greeting() {
        System.out.println("执行了greeting方法");
        return userService.greeting();
    }
}