package com.century.dao;

import com.century.vo.User;

public interface UserDAO {
    User queryUserById(int id);
    int updatePassword(User user);
    String queryPasswordByUserName(String userName);
    int queryIdByUserName(String userName);
    User queryInfoByUserName(String userName);
    User queryUserByName(String userName);
    int insert(User user);
}
