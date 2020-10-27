学习笔记

#### javac 指定UTF-8编码方式 编译一个java文件 ####
`PS C:\Git_Hub\JAVA-000\Week_02\src> javac -encoding UTF-8 .\GCLogAnalysis.java`

#### java 指定-XX:+PrintGCDetails参数 执行并查看GC信息 ####
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 64170K->10236K(74752K)] 64170K->24521K(245760K), 0.0049048 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 74240K->10220K(139264K)] 88525K->45466K(310272K), 0.0071192 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 138746K->10227K(139264K)] 173991K->85476K(310272K), 0.0089680 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 139251K->10229K(268288K)] 214500K->130123K(439296K), 0.0097853 secs] [Times: user=0.03 sys=0.08, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 10229K->0K(268288K)] [ParOldGen: 119893K->118278K(255488K)] 130123K->118278K(523776K), [Metaspace: 2703K->2703K(1056768K)], 0.0195353 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 258048K->10234K(268288K)] 376326K->198410K(523776K), 0.0136780 secs] [Times: user=0.01 sys=0.11, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 10234K->0K(268288K)] [ParOldGen: 188175K->176089K(373248K)] 198410K->176089K(641536K), [Metaspace: 2703K->2703K(1056768K)], 0.0252360 secs] [Times: user=0.13 sys=0.00, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 258048K->86183K(485376K)] 434137K->262273K(858624K), 0.0138525 secs] [Times: user=0.06 sys=0.06, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 485031K->102396K(510464K)] 661121K->354947K(883712K), 0.0269406 secs] [Times: user=0.03 sys=0.22, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 510460K->158198K(739840K)] 763011K->441842K(1113088K), 0.0309740 secs] [Times: user=0.08 sys=0.11, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 739830K->199159K(780800K)] 1023474K->537379K(1154048K), 0.0359180 secs] [Times: user=0.09 sys=0.16, real=0.03 secs]
[Full GC (Ergonomics) [PSYoungGen: 199159K->0K(780800K)] [ParOldGen: 338219K->339424K(570880K)] 537379K->339424K(1351680K), [Metaspace: 2703K->2703K(1056768K)], 0.0474201 secs] [Times: user=0.34 sys=0.00, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 581632K->156387K(1049088K)] 921056K->495812K(1619968K), 0.0230500 secs] [Times: user=0.02 sys=0.11, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 951011K->202017K(1053696K)] 1290436K->606919K(1624576K), 0.0360631 secs] [Times: user=0.08 sys=0.13, real=0.04 secs]
执行结束!共生成对象次数:15658
Heap
 PSYoungGen      total 1053696K, used 701994K [0x000000076ce00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 794624K, 62% used [0x000000076ce00000,0x000000078b642308,0x000000079d600000)
  from space 259072K, 77% used [0x000000079d600000,0x00000007a9b48520,0x00000007ad300000)
  to   space 278016K, 0% used [0x00000007af080000,0x00000007af080000,0x00000007c0000000)
 ParOldGen       total 570880K, used 404901K [0x00000006c6a00000, 0x00000006e9780000, 0x000000076ce00000)
  object space 570880K, 70% used [0x00000006c6a00000,0x00000006df5697f0,0x00000006e9780000)
 Metaspace       used 2709K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 291K, capacity 386K, committed 512K, reserved 1048576K
PS C:\Git_Hub\JAVA-000\Week_02\src>
```

#### JVM线程在线分析器 需要上传内存快照文件 ####
> https://fastthread.io/

# JVM 问题分析调优经验 #
- 1.高分配速率(High Allocation Rate)
  表示单位时间内分配的内存量。通常使用 MB/sec 作为单位。上一次垃圾收集之后，与下一次GC开始之前年轻代使用量，两者的差值除以时间，就是分配速率。

  分配速率过高就会严重影响程序的性能，在JVM中可能会导致巨大的GC开销。
  正常系统： 分配速率较低 ~ 回收速率 -> 健康
  内存泄漏： 分配速率 持续大于 回收速率 -> OOM
  性能劣化： 分配速率较高 ~ 回收速率 -> 亚健康
  总结：分配速率意味着GC次数太多。在某些情况下增加YoungGen大小，可以降低分配速率过高所造成的影响。
  增加年轻代空间并不会降低分配速率，但是会减少GC的频率。

  - 2.过早提升(Premature Promotion)
    提升速率(Promotion rate)用于衡量单位时间内从年轻代提升到老年代的数据量。一般使用 MB/sec 作为单位，和分配速率类似。

    。。。

    GC之前和之后的年轻代。。。

### 压力测试 ###
- superbenchmarker

# 20201024 Java NIO 模型 #
## NIO SocketService演进 例子  ##
- 1.最初单线程处理Socket
- 2.进化多线程，每个请求创建一个线程
- 3.优化固定大小线程池处理

## 通信模型 ##
### 思考概念： 阻塞、非阻塞、同步、异步 ###
- 1.有什么关系和区别？
- 同步异步 是通信模式。针对业务处理模式，90%业务都是同步；一般批处理操作用异步处理，例如：定时启动，定期另一个进程来捞处理信息反馈给用户。
- 阻塞、非阻塞 是 线程处理模式

### Netty 概览 ###
- 1. 异步
- 2. 事件驱动
- 3. 基于NIO
- 适用于

### Netty特性 ###
> - 高性能的协议服务器：
>> * 高吞吐
>> * 低延迟
>> * 低开销
>> * 零拷贝
>> * 可扩容
>> * 松耦合：网络和业务逻辑分离
>> * 使用方便、可维护性好
