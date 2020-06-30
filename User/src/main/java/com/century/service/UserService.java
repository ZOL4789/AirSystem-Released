package com.century.service;

import com.century.dao.UserDAO;
import com.century.vo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

    public User queryUserById(int id){
        return userDAO.queryUserById(id);
    }
}
