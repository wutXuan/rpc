package com.rpc.provider;

import com.rpc.common.model.User;
import com.rpc.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名称为：" + user.getName());
        return user;
    }
}
