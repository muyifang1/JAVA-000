package io.github.kimmking.gateway.outbound.myhttpclient;

import io.github.kimmking.gateway.outbound.httpclient4.NamedThreadFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * MyHttpOutboundHandler
 * 自定义 HttpOutboundHandler 底层实现通过 HttpClient
 *
 * @author YangQi
 */
public class MyHttpOutboundHandler {

    // 参照例子这里使用了异步的client
    private CloseableHttpAsyncClient httpClient;
    // 线程池
    private ExecutorService proxyService;

    private String backendUrl;

    /**
     * 有参构造函数
     *
     * @param backendUrl 访问终端URL
     */
    public MyHttpOutboundHandler(String backendUrl) {

        // 去掉URL末尾'/'
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
        System.out.println("backendUrl = " + backendUrl);
        // 线程池大小
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        int keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

        // 这里注意 NamedThreadFactory
        proxyService = new ThreadPoolExecutor(poolSize, poolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"),
                rejectedExecutionHandler);

        // 配置参数
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(poolSize)
                .setRcvBufSize(32 * 1024)
                .build();

        // 创建异步httpClient对象，并设置参数
        httpClient = HttpAsyncClients.custom()
                .setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioReactorConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        // 启动 httpClient 对象
        httpClient.start();
    }

    /**
     * 根据请求request和封装context 把结果封装成 response
     *
     * @param fullHttpRequest 请求request
     * @param context         ChannelHandlerContext
     */
    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext context) {

        final String url = this.backendUrl + fullHttpRequest.uri();

        // 查看 nio是否成功加入header
        System.out.println("fullHttpRequest = " + fullHttpRequest.headers());
        // 具体处理fetchGet注册到线程池中
        proxyService.submit(() -> fetchGet(fullHttpRequest, context, url));
    }

    /**
     * 线程执行Get方法
     *
     * @param fullHttpRequest 请求request
     * @param context         ChannelHandlerContext
     * @param url             URL
     */
    private void fetchGet(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext context, final String url) {

        final HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse endpointResponse) {
                // 最终处理封装response
                handleResponse(fullHttpRequest, context, endpointResponse);
            }

            @Override
            public void failed(Exception e) {
                httpGet.abort();
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    /**
     * 访问实际后端请求，并封装response
     *
     * @param fullHttpRequest  请求request
     * @param context          ChannelHandlerContext
     * @param endpointResponse 实际返回结果endpointResponse
     */
    private void handleResponse(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext context,
                                final HttpResponse endpointResponse) {

        // 封装返回结果
        FullHttpResponse response = null;

        try {

            // 实际返回数据 转成二进制数组
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            // new一个httpResponse，把后端真实数据包装进该对象
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1
                    , HttpResponseStatus.OK
                    , Unpooled.wrappedBuffer(body));
            // 控制台输出 headers 内容
            System.out.println("=======================");
            System.out.println("test endpointResponse headers" + endpointResponse.getAllHeaders());
            Arrays.asList(endpointResponse.getAllHeaders()).forEach(System.out::println);
            System.out.println("=======================");
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length"
                    , Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

        } catch (IOException e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            context.close();
        } finally {
            // 最终把response写入 ChannelHandlerContext
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    context.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    context.write(response);
                }
            }
            // 刷新 ChannelHandlerContext
            context.flush();
        }
    }
}
