学习笔记

## java 函数式编程(lambda表达式) ##
- java中函数不是一等公民，java是面向接口的开发语言
- 方法需要封装到接口里 从而 Java Lambda表达式 -> 匿名内部类

1. 不需要参数，返回值为5
`()->5`
2. 接收一个参数(数字类型)，返回其2倍的值
`x->2*x`
3. 接受两个参数
。。。

` Runnable task = () -> System.out.println(1); `
- 解释：Runnable中有一个 void run() 无参数无返回值方法。
- 理解上面例子 task是一个Runnable实例化对象，其中run直接调用执行了 打印1

## java函数式编程基础 J.U.F包 java.util.function ##
- 注解@FunctionalInterface
- Predicate<T> 有参数、条件判断
- Function<T,R> 有参数、有返回值  `R apply(T t)`
- Consumer<T> 有参数、无返回值  `void accept(T t)`
- Supplier<T> 无参数、有返回值  `T get()`

- 进一步简化 System.out::println;

## java 集合与泛型 的延伸 Stream ##
- 先说一个观点:java中泛型是伪泛型 `List <T>` 代码在编译后T就不存在了。这点在反序列化时会有问题，代码中有例子 GenericDemo。
- 泛型的用法 需要整理 时间 21:05
- Stream(流)是一个来自数据源的元素队列并支持聚合操作
  + 元素
  + 数据源 ：流的来源 数组，集合类，
  + 聚合操作 例如：filter, map, reduce, find, match, sorted
  + 和以前的Collection操作不同
  + Pipelining
  + 内部迭代:以前对集合遍历都是通过Iterator或者For-Each方式，显示在集合外部进行迭代。Stream提供了内部迭代的方式，通过访问者模式(Visitor)实现。


## Lombok是什么 ##
1. Lombok是基于jsr269实现的一个非常神奇的类库。
2. 需要配合 Lombok 插件，在编译.class文件时 自动加入了一些通用代码。
- @Setter @Getter
- @Data
- @XXXConstructor
- @Slf4j

## 什么是Guava ##
1. Guava是一种基于开源的java库。Guava对JDK集合的扩展，这是Guava最成熟和为人所知的部分。
- 集合 [Collections]
- 缓存 [Caches] 本地缓存实现，支持多种缓存过期策略。
- 并发 [Concurrency] ListenableFuture:完成后触发回调的Future。
- 字符串处理[Strings] 非常有用的字符串工具，包括分割、链接、填充等操作
- 时间总线 [EventBus] 发布-订阅模式的组件通信，进程内模块见解耦 // todo 重点理解 22:25
- 反射 [Reflection] Guava的Java反射机制工具类

### SOLID 面向对象设计原则 ###
1. SRP
2. OCP

## 模式三个层次 ##
1. 解决方案层面（架构模式）
2. 组件层面（框架模式）
3. 代码层面（GoF设计模式）

### 其他模式 ###
- 集成模式
- 事务模式
- IO模式/Context模式
- 甚至状态机FSM
- 规则引擎RE
- workflow都是模式

## GoF 23设计模式 ##
### 创建型 ###
1. Factory Method
2. Abstract Factory
3.
4.
5.
### 结构型 ###
6.
7.
8.
9.
10.
11.
12.
### 行为型 ###
13.
14.
15.
15.
16.
17.
18.
19.
20.
21.
22.
23.
