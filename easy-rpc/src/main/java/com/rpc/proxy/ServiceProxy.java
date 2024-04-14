package com.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.rpc.RpcApplication;
import com.rpc.config.RpcConfig;
import com.rpc.constant.RpcConstant;
import com.rpc.model.RpcRequest;
import com.rpc.model.RpcResponse;
import com.rpc.model.ServiceMetaInfo;
import com.rpc.registry.Registry;
import com.rpc.serializer.JdkSerializer;
import com.rpc.serializer.Serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Serializer serializer = new JdkSerializer();

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try{
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心获取提供者的请求地址
            RpcApplication.init();
            Registry registry = RpcApplication.getRegistry();
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(method.getDeclaringClass().getName());
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);

            List<ServiceMetaInfo> serviceMetaInfos = registry.serviceDiscovery(serviceMetaInfo.getServiceNodeKey());

            if(CollUtil.isEmpty(serviceMetaInfos)){
                throw new RuntimeException("暂无服务地址");
            }

            ServiceMetaInfo serviceMetaInfo1 = serviceMetaInfos.get(0);

            // 发送请求
//            HttpResponse httpResponse = HttpRequest.post("http://localhost:8081").body(bodyBytes).execute();
            HttpResponse httpResponse = HttpRequest.post(serviceMetaInfo1.getServiceAddress()).body(bodyBytes).execute();
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result,RpcResponse.class);
            return rpcResponse.getData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
