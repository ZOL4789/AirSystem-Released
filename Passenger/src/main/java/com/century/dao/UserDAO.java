package com.century.dao;

import com.century.vo.User;

public interface UserDAO {
    User queryUserById(int id);
}
