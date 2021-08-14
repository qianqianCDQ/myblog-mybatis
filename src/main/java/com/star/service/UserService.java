package com.star.service;

import com.star.entity.User;


public interface UserService {

    // 核对用户名和密码
    User checkUser(String username, String password);

    User getUserById(long id);
}