package com.rpc.config;

import lombok.Data;

@Data
public class RpcConfig {
    private String name = "xuan";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer port = 8081;
}
