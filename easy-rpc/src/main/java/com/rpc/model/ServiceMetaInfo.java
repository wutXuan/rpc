package com.rpc.model;

import com.rpc.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceMetaInfo {
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 服务地址
     */
    private String serviceAddress = "http://localhost:8081";

    /**
     * 服务分组（未实现）
     */
    private String serviceGroup = "default";

    public String getServiceKey(){
        return String.format("%s:%s",serviceName,serviceVersion);
    }

    public String getServiceNodeKey(){
        return String.format("%s%s",getServiceKey(),serviceAddress);
    }
}
