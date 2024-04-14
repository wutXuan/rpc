package com.rpc.consumer;

import com.rpc.common.model.User;
import com.rpc.common.service.UserService;
import com.rpc.config.ConfigUtils;
import com.rpc.config.RpcConfig;
import com.rpc.proxy.ServiceProxyFactory;

public class ConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("xuanxuan");
        User newUser = userService.getUser(user);
        if(newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("newUser为null");
        }
    }
}
