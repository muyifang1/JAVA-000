package io.github.kimmking.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private String proxyServer;

    public HttpInboundInitializer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        // 取得pipeline
        ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}

        // 这里给pipeline添加各种处理器
        // pipeline添加 HttpServer编码器
        p.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        // pipeline添加 对象参数聚合器
        p.addLast(new HttpObjectAggregator(1024 * 1024));
        // 我们添加的InboundHandler
        p.addLast(new HttpInboundHandler(this.proxyServer));
    }
}
