package io.github.kimmking.gateway;

import io.github.kimmking.gateway.inbound.HttpInboundServer;

import java.util.ArrayList;

/**
 * Netty Server 网关
 * 前端访问8888端口 跳转至后台8088实际端口
 *
 * @author YangQi
 */
public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        //  http://localhost:8888/api/hello  ==> gateway API
        //  http://localhost:8081/api/hello  ==> backend service
        //  http://localhost:8082/api/hello  ==> backend service
        //  http://localhost:8083/api/hello  ==> backend service

        // 前端访问 8888端口 跳转至后台8088实际端口
        String proxyServer1 = System.getProperty("proxyServer", "http://localhost:8081");
        String proxyServer2 = System.getProperty("proxyServer", "http://localhost:8082");
        String proxyServer3 = System.getProperty("proxyServer", "http://localhost:8083");
        ArrayList<String> serverList = new ArrayList<>();
        serverList.add(proxyServer1);
        serverList.add(proxyServer2);
        serverList.add(proxyServer3);
        String proxyPort = System.getProperty("proxyPort", "8888");

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        // 调用 inboundServer
        HttpInboundServer server = new HttpInboundServer(port, serverList);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + serverList);
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
