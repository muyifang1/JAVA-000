学习笔记

# 2020/10/29 #
## 什么是高性能？ ##
- 1. 高并发用户(Concurent Users)
- 2. 高吞吐量(Throughtout)
- 3. 低延迟(Latency)

## 应对策略 ##
- 1.容量
>### 每天 八万六千四百秒 ###
>>  * taobao 每天三千万订单， tps约300~400 ; 如果压缩为八小时 约 1000
>>  * 去年双十一 支付宝

- 2.爆炸半径
- 3.工程方面积累与改进

## 关键对象  BECH (BootStrap,EventLoop,Channel,Handler) ##
- BootStrap：启动线程，开启socket
- EventLoopGroup （相当于线程池）
- EventLoop （单线程）
- SocketChannel：链接
- ChannelInitalizer:初始化 ->需要注入到应用里
- ChannelPipeline：处理器链
- ChannelHandler：处理器

## Event & Handler ##
### 入站事件： ###
 * 通道激活和停用
 * 读操作事件
 * 异常事件
 * 用户事件
### 出站事件： ###
 * 打开链接
 * 关闭链接
 * 写入数据
 *

## Netty 网络程序化 ##
粘包与拆包 ： 在网络上传递数据是一个一个数据包发送，中间有缓冲区
ByteToMessageDecoder 提供一些常见的实现类：
 - 1. FixedLengthFrameDecoder: 定长协议解码器，我们可以指定固定的字节数算一个完整的报文
 - 2. LineBaseFrameDecoder: 行分隔符解码器，遇到\n 或者 \r\n ， 则认为是一个完整的报文
 - 3. DelimiterBasedFrameDecoder：分隔符解码器，分隔符可以自己指定
 - 4. LengthFieldBasedFrameDecoder：将报文划分为报文头/报文体
 - 5. JsonObjectDecoder：json 格式解码器，当检测到匹配数量的{}或者[]时，则认为是完整的json对象或者json数组

## Netty 优化 ##
 1. 不要阻塞 EventLoop
 2. 系统参数化 ulimit -a    /proc/sys/net/ipv4/tcp_fin_timeout, TcpTimedWaitDelay
 3. 缓冲区优化 SO_RCVBUF/SO_SNDBUF/SO_BACKLOG/REUSEXXX
 4. 心跳周期优化
 5. 内存与 ByteBuffer 优化
 6. 优化其他

## 典型应用：API网关 ##
网关分类：
流量网关（Ngix）  --API Gateway Req/Resp--  业务网关（Zuul / Spring Cloud Gateway）

Zuul 2.x 是基于 Netty 内核重构的版本。
核心功能：
1. Service Discovery
2. Load Balancing
3. Connection

https://github.com/kimmking/JavaCourseCodes

## 工作中遇到问题，需要做下面判断 ##
1. 判断当前问题是真的问题？还是情绪问题？
2. 技术问题？还是管理问题？
3. 技术复杂度问题？还是业务复杂度问题？

# Java 多线程 #
1. 注意守护线程概念 Daemon 参看 https://www.cnblogs.com/xiarongjin/p/8310144.html

## Thread 状态
1. RR Ready Runalbe
2. WW Waiting 、Timed Waiting
3. B Blocked
