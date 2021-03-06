学习笔记

# java.util.concurrency #
- 什么是锁
-
- 乐观锁 CAS Compare and Swap

* 并发低，有锁无锁几乎无差异
* 并发高无竞争，乐观锁，自旋比较好
* 并发高有竞争，悲观锁，排队等待好

## 什么是并发工具类 ##
1. 多线程之间怎么相互协作？
- AQS AbstractQueuedSynchronizer 抽象队列同步器。他是构建锁或者其他同步组件的基础。是 JUC 并发包的核心基础组件。
- 两种资源共享方式：独占|共享，子类负责实现公平 OR 非公平

## JUC 进化 ##
1. synchronized/wait 锁
2. 演进到 显示锁 Lock 可以传入条件 Condition
3. 过渡 可见性 volatile 原子性 JUC.atomic 原子类包
4. 演进到 线程池 new Thread()管理
5. 演进到 线程间协作信号量 工具类 抽象队列排队器(AbstractQueuedSynchronizer) Setmaphore、CountDownLatch、CyclicBarrier
6. 演进到 线程安全集合类

## 加锁需要考虑的问题 ##
1. 粒度
2. 性能
3. 重入
4. 公平
5. 自旋锁
6. 场景：脱离业务场景谈性能都是耍流氓
