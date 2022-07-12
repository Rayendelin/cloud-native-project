package com.example.cloudnativeproject.Dao;

import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private String msg = "Hello!";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}