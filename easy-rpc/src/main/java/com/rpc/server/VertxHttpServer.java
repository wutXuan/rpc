package com.rpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        // 创建vertx实例
        Vertx vertx = Vertx.vertx();
        // 创建http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 当监听的端口有请求发过来时，处理请求
        server.requestHandler(new HttpServerHandler());

        // 启动服务器并监听端口
        server.listen(port,result -> {
            if(result.succeeded()){
                System.out.println("Listening port:" + port);
            }else{
                System.err.println("Start Failed:" + result.cause());
            }
        });
    }
}
