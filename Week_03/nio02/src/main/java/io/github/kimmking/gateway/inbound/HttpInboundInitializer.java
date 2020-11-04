package io.github.kimmking.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

/**
 * HttpInboundInitializer 初始化Channel
 *
 * @author YangQi
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> serverList;

    public HttpInboundInitializer(List<String> serverList) {
        this.serverList = serverList;
    }

    @Override
    public void initChannel(SocketChannel channel) {
        // 取得pipeline
        ChannelPipeline pipeline = channel.pipeline();

//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
        /**
         * 这里给pipeline添加各种处理器
         */

        // pipeline添加 HttpServer编码器
        pipeline.addLast(new HttpServerCodec());
        // pipeline添加 对象参数聚合器
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        // 添加我们的InboundHandler
        pipeline.addLast(new HttpInboundHandler(this.serverList));
    }
}
