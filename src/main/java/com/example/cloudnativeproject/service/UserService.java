package com.example.cloudnativeproject.service;

import com.example.cloudnativeproject.Dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String greeting(){
        return userDao.getMsg();
    }
}
