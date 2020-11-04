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

public class HttpInboundServer {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    private int port;

    private String proxyServer;

    // router时 需要proxyServer 传入多个 根据条件进行路由
    public HttpInboundServer(int port, String proxyServer) {
        this.port = port;
        this.proxyServer = proxyServer;
    }

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 128)  // 建立连接过程中，未完成握手的连接可以保存多少（Linux和Mac 默认128,win 默认200）
                    .option(ChannelOption.TCP_NODELAY, true) // Nodelay 算法开关(粘包传输)
                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持底层长连接
                    .option(ChannelOption.SO_REUSEADDR, true) // 是否复用地址
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024) // receive 缓冲区大小
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024) // send 缓冲区大小
                    .option(EpollChannelOption.SO_REUSEPORT, true) // 是否复用端口
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT); // 指定配置ByteBuffer内存池

            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)).childHandler(new HttpInboundInitializer(this.proxyServer)); // 代理服务器参数传入

            Channel ch = b.bind(port).sync().channel();
            logger.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
