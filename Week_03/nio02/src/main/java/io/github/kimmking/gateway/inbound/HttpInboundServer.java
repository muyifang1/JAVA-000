package io.github.kimmking.gateway.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Inbound Server
 *
 * @author YangQi
 */
public class HttpInboundServer {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    private int port;

    private List<String> serverList;

    /**
     * @param port       访问端口
     * @param serverList 后台真实服务
     */
    public HttpInboundServer(int port, List<String> serverList) {
        this.port = port;
        this.serverList = serverList;
    }

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap serverBootStrap = new ServerBootstrap();
            // 参数说明
            // ChannelOption.SO_BACKLOG 建立连接过程中，未完成握手的连接可以保存多少（Linux和Mac 默认128,win 默认200）
            // ChannelOption.TCP_NODELAY Nodelay 算法开关(粘包传输)
            // ChannelOption.SO_KEEPALIVE 保持底层长连接
            // ChannelOption.SO_REUSEADDR 是否复用地址
            // ChannelOption.SO_RCVBUF receive 缓冲区大小
            // ChannelOption.SO_SNDBUF send 缓冲区大小
            // EpollChannelOption.SO_REUSEPORT 是否复用端口
            // ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT 指定配置ByteBuffer内存池

            serverBootStrap.option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // Channel 指定使用NioServer 模型
            // handler 指定调用 HttpInboundInitializer 并把后台实际访问地址作为入参
            serverBootStrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new HttpInboundInitializer(this.serverList));

            Channel channel = serverBootStrap.bind(port).sync().channel();
            logger.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
