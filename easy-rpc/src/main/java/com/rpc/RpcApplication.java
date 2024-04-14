package com.rpc;

import com.rpc.config.ConfigUtils;
import com.rpc.config.RegistryConfig;
import com.rpc.config.RpcConfig;
import com.rpc.constant.RpcConstant;
import com.rpc.registry.EtcdRegistry;
import com.rpc.registry.Registry;

public class RpcApplication {
    private static volatile RpcConfig rpcConfig;

    private static volatile Registry registry;

    public static void init(RpcConfig newRpcConfig){
        rpcConfig = newRpcConfig;
        RegistryConfig registryConfig = new RegistryConfig();

        registry = new EtcdRegistry();
        registry.init(registryConfig);
    }

    public static void init(){
        RpcConfig newRpcConfig = null;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            // 加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig(){
        if(rpcConfig == null){
            synchronized (RpcApplication.class){
                if(rpcConfig == null){
                    init();
                }
            }
        }
        return rpcConfig;
    }

    public static Registry getRegistry(){
        return registry;
    }
}
