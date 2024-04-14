package com.rpc.provider;

import com.rpc.RpcApplication;
import com.rpc.common.service.UserService;
import com.rpc.model.ServiceMetaInfo;
import com.rpc.registry.LocalRegistry;
import com.rpc.registry.Registry;
import com.rpc.server.VertxHttpServer;

public class ProviderExample {
    public static void main(String[] args) {
        // 注册方法到本地
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);
        RpcApplication.init();

        // 注册url:localhost:8081 到注册中心
        String serviceName = UserService.class.getName();
        Registry registry = RpcApplication.getRegistry();
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);

        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //开启服务器容器，监听localhost:8081端口
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.doStart(RpcApplication.getRpcConfig().getPort());
    }
}
