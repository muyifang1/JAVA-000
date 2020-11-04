package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HttpHeadersFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.outbound.myhttpclient.MyHttpOutboundHandler;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.MyHttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 具体InboundHandler
 *
 * @author YangQi
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final List<String> serverList;
    // private HttpOutboundHandler handler;

    private MyHttpOutboundHandler handler;
    private HttpRequestFilter filter;
    private HttpEndpointRouter router;

    public HttpInboundHandler(List<String> serverList) {
        this.serverList = serverList;
        filter = new HttpHeadersFilter();
        router = new MyHttpEndpointRouter();
        // 初始化时绑定需要的outboundHandler
        // handler = new HttpOutboundHandler(this.proxyServer);
        handler = new MyHttpOutboundHandler(router.route(this.serverList));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
//            String uri = fullRequest.uri();
//            //logger.info("接收到的请求url为{}", uri);
//            if (uri.contains("/test")) {
//                handlerTest(fullRequest, ctx);
//            }

            /**
             * 这里可以追加 filter
             */
            filter.filter(fullRequest, ctx);
            // 调用outbandHandler处理，把请求request和封装响应context传入，把结果封装成response
            handler.handle(fullRequest, ctx);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
