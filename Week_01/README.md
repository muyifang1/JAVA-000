学习笔记 20201015
#JVM #
##Java 字节码 ##
###Java bytecode 由单字节(byte)指令组成，理论上最多支持256个操作码(opcode) ###
* 根据指令的性质分四类：
* 1.栈操作指令，包括与局部变量交互的指令
* 2.程序流程控制指令
* 3.对象操作指令，包括方法调用指令
* 4.算术运算以及类型转换指令

### 类的加载时机 ###
* 1.当虚拟机启动时，初始化用户指定的主类，就是执行main方法所在类；
* 2.new指令
* 3. 调用静态方法的指令
* 4.
* 5.
* 6.
* 7.

### 类不会被初始化(可能会加载) ###
* 1.通过子类引用父类的静态字段，只会触发父类的初始化，而不会触发子类的初始化。
* 2.定义对象数组，不会触发该类的初始化。（只有new类对象，放入该数组时才会初始化）
* 3.常亮在编译期间会存入调用类的常量池
* 4.通过类名获取 Class对象，不会触发类的初始化
* 5.
* 6. 通过ClassLoade默认的

### 三类加载器 ###
* 1.启动类加载器(BootstrapClassLoader)
* 2.扩展类加载器(ExtClassLoader)
* 3.应用类加载器(AppClassLoader)
BootstrapClassLoader -> ExtClassLoader -> AppClassLoader -> Custom ClassLoader 1

加载器特点
* 1.双亲委托
* 2.负责依赖
* 3.缓存加载

## JVM Xmx参数内存 指定不要高于物理极限内存的70% ##

### JVM 启动参数 ###
* 以-开头为标准参数，所有的JVM都要实现这些参数，并且向后兼容
* -D 设置系统属性
* -X 开头为非标准参数，基本都是创给JVM的，默认JVM实现这些参数的功能，但是并不保证所有JVM都实现满足，且不保证向后兼容。
  可以使用 java -X 命令查看当前JVM支持的非标准参数
* -XX 开头为非稳定参数，专门用于控制JVM的行为，跟具体的JVM实现有关，随时可能会在下一个版本取消。
  -XX:+-Flags 形式，+-是对布尔值进行开关。
  -XX：key=value 形式，指定某个选项的值。
### JVM 启动参数 --堆内存 ###
* -Xmx 指定最大堆内存
* -Xms 指定堆内存空间的初始大小。专用服务器上需要-Xms和-Xmx一致，否则应用则刚启动可能就有好几个Full GC。两者配置不一致时，堆内存扩容可能导致性能抖动。
* -Xmn 等价于-XX：NewSize，设置年轻代空间。注意：使用 G1GC时不应该设置该选项。其他场景下可以设置 官方建议 -Xmx的1/2~1/4。
* -XX:MaxPermSize=size, JDK1.7之前使用。Java8默认允许的Meta空间无限大，此参数无效。
* -XX:MaxMetaspaceSize=size,Java8默认不限制Meta空间，一般不允许设置该选项。
* -XX:MaxDirectMemorySize=size,系统可以使用的最大堆外内存，这个参数跟 -Dsun.nio.MaxDirectMemorySize效果相同。
* -Xss，设置每个线程栈的字节数。例如 -Xss1m 指定线程栈为1MB，等价于-XX:ThreadStackSize=1m。

## JVM 命令 ###
##### jps/jinfo 查看Java进程 #####
##### jstat 查看JVM内部GC相关信息 #####
##### jmp 查看heap或类占用空间统计 #####
##### jstack 查看线程信息 #####
##### jcmd 执行JVM相关分析命令(整合命令) #####
##### jrunscript/jjs 执行js命令 #####

* jps
> C:\Git_Hub\JAVA-000>jps
> 9160 Jps
> 9404


* jps -lmv
* jinfo

* jstat -gc 9404 1000 1000
>C:\Git_Hub\JAVA-000>jstat -gc 9404 1000 1000
> S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
>4352.0 4352.0 1432.1  0.0   34944.0  32586.2   452216.0   355502.1  286812.0 277660.6 39200.0 35810.1    122    0.857   0      0.000    0.857
>4352.0 4352.0 1432.1  0.0   34944.0  34546.6   452216.0   355502.1  286812.0 277660.6 39200.0 35810.1    122    0.857   0      0.000    0.857
>4352.0 4352.0  0.0   1297.3 34944.0   1254.1   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   1967.1   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   3276.0   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   4616.2   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   6581.4   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   7273.7   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   7976.1   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0   9885.5   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0  29697.4   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0  32280.5   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0  0.0   1297.3 34944.0  34180.0   452216.0   355572.0  286812.0 277832.5 39200.0 35811.1    123    0.860   0      0.000    0.860
>4352.0 4352.0 1551.4  0.0   34944.0   699.7    452216.0   355677.3  287068.0 277869.5 39200.0 35811.1    124    0.863   0      0.000    0.863


>C:\Git_Hub\JAVA-000\Week_01\src\jvm\demo>jps
7384 Jps
9404

jcmd pic VM.version
>C:\Git_Hub\JAVA-000\Week_01\src\jvm\demo>jcmd 9404 VM.version
9404:
OpenJDK 64-Bit Server VM version 11.0.7+10-b944.20
JDK 11.0.7

## JVM 监控工具 ##
- jmc 最强大，有飞行记录功能
- jvisualvm
- VisualGC IDEA插件
- jconsole 最简单，个人感觉分辨率不好

# GC 的背景与一般原理 #
- 引用计数：仓库与引用计数：计数为0时，销毁
- 实际情况复杂一点，因为仓库与仓库之间有关系（引用，相互调用）
  假如 两个对象相互引用，计数永远不为0，形成一种环状。（原理跟死锁一致）
  解决方案：引用跟踪！ 从根对象出发并标记可达对象，不可达的未标记对象销毁。
- 清除算法：标记-清除(Mark-Sweep)
- 复制算法：标记-复制算法(Mark-Copy)
- 整理算法：标记-清除-整理算法(Mark-Sweep-Compact)
## 并行GC 和 CMS 的基本原理 都是从根对象出发
如何做到标记和清除 上百万对象？
STW，让全世界暂停，然后快照 进行标记

分代GC策略不同
老年代(Young-gen)， 年轻代(Old-gen)，新生代(Eden-Space)->(S0, S1)
每一代回收策略不同，理论上说存活时间越长，回收频率被调用越少

年轻代：复制法 Minor GC
老年代：整理法（移动对象） Major GC
FullGC

下面参数控制15代后仍然存活的对象将移动到老年代
```
-XX: +MaxTenuringThreshold=15
```

### GC 对比 ###
|收集器|串行、并行or并发|新生代/老年代|算法|目标|适用场景|
|:-|:-:|:-:|:-:|:-:|-:|
|Serial|串行|新生代|复制算法|响应速度优先|单CPU环境下的Client模式|
|Serial|串行|老年代|标记-整理|响应速度优先|单CPU环境下Client模式，CMS的后备预案|
|ParNew|并行|新生代|复制算法|响应速度优化|多CPU环境时在Server模式下与CMS配合|
|Parallel|并行|新生代|复制算法|吞吐量优先|后台运算而不需要太多交互的任务|
|Parallel Old|并行|老年代|标记-整理|吞吐量优先|在后台运算而不需要太多交互的任务|
|CMS|并发|老年代|标记-清除|响应速度优先|集中在互联网站或B/S系统服务端上的Java应用|
|G1|并发|both|标记-整理+复制算法|响应速度优先|面向服务端应用，将替代CMS|

### 选择 GC ROOTS 根对象原则： ###
- 1. 当前正在执行的方法里的局部变量和输入参数
- 2. 活动线程(Active threads)
- 3. 所有类的静态字段(static field)
- 4. JNI 引用
还要有一个集合用来记录跨代引用记录 rememberSet

串行GC(Serial GC) / ParNewGC

并行 GC (Parallel GC)
```
-XX: +UseParallelGC
-XX: +UseParallelOldGC
-XX: +UseParallelGC
-XX: +UseParallelOldGC
```

ZGC 主要的特点：
- 1. GC最大停顿时间不超过10ms
- 2. 堆内存支持范围广，小至几百MB的堆空间，大至4TB超大堆内存(JDK13 升级至 16TB)
- 3. 与G1相比，应用吞吐量下降不超过15%
- 4. 当前只支持 Linux/x64 平台， JDK后支持 MacOS 和 Windows 系统

--------------------
#### javap -c 反汇编 HelloJVMDemo.class ####
```
C:\Git_Hub\JAVA-000\Week_01\src\jvm\demo>javap -c HelloJVMDemo.class
Compiled from "HelloJVMDemo.java"
public class jvm.demo.HelloJVMDemo {
  public jvm.demo.HelloJVMDemo();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: ldc           #2                  // String Hello
       7: putfield      #3                  // Field hello:Ljava/lang/String;
      10: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #13                 // class jvm/demo/HelloJVMDemo
       3: dup
       4: invokespecial #14                 // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: invokespecial #15                 // Method helloJVM:()V


      12: return
}
```
知识点 1：
>        0: aload_0
>        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
>        4: aload_0
这是调用父类构造方法 即执行 super()调用，上面例子中父类是 Object

-----------------------
#### javap -c -v 反汇编同时输出附加信息 HelloJVMDemo.class ####
```
C:\Git_Hub\JAVA-000\Week_01\src\jvm\demo>javap -c -v HelloJVMDemo.class
Classfile /C:/Git_Hub/JAVA-000/Week_01/src/jvm/demo/HelloJVMDemo.class
  Last modified 2020-10-18; size 810 bytes
  MD5 checksum 538b0050c7d7c7bdc0f1a7d3ddfe1899
  Compiled from "HelloJVMDemo.java"
public class jvm.demo.HelloJVMDemo
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #16.#28        // java/lang/Object."<init>":()V
   #2 = String             #29            // Hello
   #3 = Fieldref           #13.#30        // jvm/demo/HelloJVMDemo.hello:Ljava/lang/String;
   #4 = String             #31            // JVM
   #5 = String             #32            // This is test Method!
   #6 = Fieldref           #33.#34        // java/lang/System.out:Ljava/io/PrintStream;
   #7 = Class              #35            // java/lang/StringBuilder
   #8 = Methodref          #7.#28         // java/lang/StringBuilder."<init>":()V
   #9 = Methodref          #7.#36         // java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #10 = String             #37            //
  #11 = Methodref          #7.#38         // java/lang/StringBuilder.toString:()Ljava/lang/String;
  #12 = Methodref          #39.#40        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #13 = Class              #41            // jvm/demo/HelloJVMDemo
  #14 = Methodref          #13.#28        // jvm/demo/HelloJVMDemo."<init>":()V
  #15 = Methodref          #13.#42        // jvm/demo/HelloJVMDemo.helloJVM:()V
  #16 = Class              #43            // java/lang/Object
  #17 = Utf8               hello
  #18 = Utf8               Ljava/lang/String;
  #19 = Utf8               <init>
  #20 = Utf8               ()V
  #21 = Utf8               Code
  #22 = Utf8               LineNumberTable
  #23 = Utf8               helloJVM
  #24 = Utf8               main
  #25 = Utf8               ([Ljava/lang/String;)V
  #26 = Utf8               SourceFile
  #27 = Utf8               HelloJVMDemo.java
  #28 = NameAndType        #19:#20        // "<init>":()V
  #29 = Utf8               Hello
  #30 = NameAndType        #17:#18        // hello:Ljava/lang/String;
  #31 = Utf8               JVM
  #32 = Utf8               This is test Method!
  #33 = Class              #44            // java/lang/System
  #34 = NameAndType        #45:#46        // out:Ljava/io/PrintStream;
  #35 = Utf8               java/lang/StringBuilder
  #36 = NameAndType        #47:#48        // append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
  #37 = Utf8
  #38 = NameAndType        #49:#50        // toString:()Ljava/lang/String;
  #39 = Class              #51            // java/io/PrintStream
  #40 = NameAndType        #52:#53        // println:(Ljava/lang/String;)V
  #41 = Utf8               jvm/demo/HelloJVMDemo
  #42 = NameAndType        #23:#20        // helloJVM:()V
  #43 = Utf8               java/lang/Object
  #44 = Utf8               java/lang/System
  #45 = Utf8               out
  #46 = Utf8               Ljava/io/PrintStream;
  #47 = Utf8               append
  #48 = Utf8               (Ljava/lang/String;)Ljava/lang/StringBuilder;
  #49 = Utf8               toString
  #50 = Utf8               ()Ljava/lang/String;
  #51 = Utf8               java/io/PrintStream
  #52 = Utf8               println
  #53 = Utf8               (Ljava/lang/String;)V
{
  public jvm.demo.HelloJVMDemo();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: ldc           #2                  // String Hello
         7: putfield      #3                  // Field hello:Ljava/lang/String;
        10: return
      LineNumberTable:
        line 8: 0
        line 10: 4

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=1
         0: new           #13                 // class jvm/demo/HelloJVMDemo
         3: dup
         4: invokespecial #14                 // Method "<init>":()V
         7: astore_1
         8: aload_1
         9: invokespecial #15                 // Method helloJVM:()V
        12: return
      LineNumberTable:
        line 23: 0
        line 24: 8
        line 25: 12
}
SourceFile: "HelloJVMDemo.java"
```
知识点 2：
>     descriptor: ([Ljava/lang/String;)V
>     flags: ACC_PUBLIC, ACC_STATIC
- 1. 小括号内是入参信息/形参信息
- 2. 左括号表示数组
- 3. L表示对象
- 4. 后面 java/lang/String 是类名称
- 5. 小括号后面的 V 则表示这个方法的返回值是 void
- 6. 方法的访问标志也很容易理解 flags: ACC_PUBLIC, ACC_STATIC， 表示public和static

知识点 3：
> stack=2, locals=2, args_size=1
- 执行该方法时需要的栈(stack)深度 2，需要在局部变量中保留多少个槽位 2，该方法参数个数 1

知识点 4：
>   public jvm.demo.HelloJVMDemo();
>     descriptor: ()V
>     flags: ACC_PUBLIC
>     Code:
>       stack=2, locals=1, args_size=1
- 这个无参数构造方法参数是 1。原因非静态方法，this将被分配到局部变量表的第0号槽位。
- 注意静态方法则没有this引用。

知识点 5：
>        0: new           #13                 // class jvm/demo/HelloJVMDemo
>        3: dup
>        4: invokespecial #14                 // Method "<init>":()V
>        7: astore_1
- 前面序号间隔不相等，原因是有一部分操作码会附带有操作数，也会占用字节码数组中的间隔。
- 例如，new会占用三个槽位：一个用于存放操作码指令自身，两个用于存放操作数。所以dup索引从3开始。
  new dup invokespecial 指令在一起时，在创建类的实例对象！
- new 指令只是创建对象，没有调用构造函数。
- invokespecial 指令用来调用某些特殊方法，这里是调用构造函数。
- dup 指令用于复制栈顶的值。
- astore_1 表示赋值给局部变量 1 是局部变量表中的位置
- putfield 将值赋给实例字段
- putstatic 将值赋给静态字段

知识点 6：
> 关于静态方法
- 如果创建某个类的新实例，访问静态字段或者调用静态方法，就会触发该类的静态初始化方法(如果尚未初始化)。
- 体现在指令集就是 new,getstatic,putstatic,invokestatic 会触发 clinit 指令。

```
C:\Git_Hub\JAVA-000\Week_01\src\jvm\demo\move\average>javac -g *.java

C:\Git_Hub\JAVA-000\Week_01\src\jvm\demo\move\average>javap -c -v LocalVariableTest.class
Classfile /C:/Git_Hub/JAVA-000/Week_01/src/jvm/demo/move/average/LocalVariableTest.class
  Last modified 2020年10月19日; size 1286 bytes
  SHA-256 checksum c8fbbd913e80a5d75dd2690ebd112c6d38a80b882f6718ec1a6e8fe961e559f0
  Compiled from "LocalVariableTest.java"
public class jvm.demo.move.average.LocalVariableTest
  minor version: 0
  major version: 58
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #34                         // jvm/demo/move/average/LocalVariableTest
  super_class: #2                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 3
Constant pool:
   #1 = Methodref          #2.#3          // java/lang/Object."<init>":()V
   #2 = Class              #4             // java/lang/Object
   #3 = NameAndType        #5:#6          // "<init>":()V
   #4 = Utf8               java/lang/Object
   #5 = Utf8               <init>
   #6 = Utf8               ()V
   #7 = Class              #8             // jvm/demo/move/average/MovingAverage
   #8 = Utf8               jvm/demo/move/average/MovingAverage
   #9 = Methodref          #7.#3          // jvm/demo/move/average/MovingAverage."<init>":()V
  #10 = Methodref          #7.#11         // jvm/demo/move/average/MovingAverage.submit:(D)V
  #11 = NameAndType        #12:#13        // submit:(D)V
  #12 = Utf8               submit
  #13 = Utf8               (D)V
  #14 = Methodref          #7.#15         // jvm/demo/move/average/MovingAverage.getAvg:()D
  #15 = NameAndType        #16:#17        // getAvg:()D
  #16 = Utf8               getAvg
  #17 = Utf8               ()D
  #18 = Fieldref           #19.#20        // java/lang/System.out:Ljava/io/PrintStream;
  #19 = Class              #21            // java/lang/System
  #20 = NameAndType        #22:#23        // out:Ljava/io/PrintStream;
  #21 = Utf8               java/lang/System
  #22 = Utf8               out
  #23 = Utf8               Ljava/io/PrintStream;
  #24 = InvokeDynamic      #0:#25         // #0:makeConcatWithConstants:(D)Ljava/lang/String;
  #25 = NameAndType        #26:#27        // makeConcatWithConstants:(D)Ljava/lang/String;
  #26 = Utf8               makeConcatWithConstants
  #27 = Utf8               (D)Ljava/lang/String;
  #28 = Methodref          #29.#30        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #29 = Class              #31            // java/io/PrintStream
  #30 = NameAndType        #32:#33        // println:(Ljava/lang/String;)V
  #31 = Utf8               java/io/PrintStream
  #32 = Utf8               println
  #33 = Utf8               (Ljava/lang/String;)V
  #34 = Class              #35            // jvm/demo/move/average/LocalVariableTest
  #35 = Utf8               jvm/demo/move/average/LocalVariableTest
  #36 = Utf8               Code
  #37 = Utf8               LineNumberTable
  #38 = Utf8               LocalVariableTable
  #39 = Utf8               this
  #40 = Utf8               Ljvm/demo/move/average/LocalVariableTest;
  #41 = Utf8               main
  #42 = Utf8               ([Ljava/lang/String;)V
  #43 = Utf8               args
  #44 = Utf8               [Ljava/lang/String;
  #45 = Utf8               ma
  #46 = Utf8               Ljvm/demo/move/average/MovingAverage;
  #47 = Utf8               num1
  #48 = Utf8               I
  #49 = Utf8               num2
  #50 = Utf8               avg
  #51 = Utf8               D
  #52 = Utf8               SourceFile
  #53 = Utf8               LocalVariableTest.java
  #54 = Utf8               BootstrapMethods
  #55 = MethodHandle       6:#56          // REF_invokeStatic java/lang/invoke/StringConcatFactory.makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Lja
va/lang/Object;)Ljava/lang/invoke/CallSite;
  #56 = Methodref          #57.#58        // java/lang/invoke/StringConcatFactory.makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)L
java/lang/invoke/CallSite;
  #57 = Class              #59            // java/lang/invoke/StringConcatFactory
  #58 = NameAndType        #26:#60        // makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;
  #59 = Utf8               java/lang/invoke/StringConcatFactory
  #60 = Utf8               (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;
  #61 = String             #62            // avg = \u0001
  #62 = Utf8               avg = \u0001
  #63 = Utf8               InnerClasses
  #64 = Class              #65            // java/lang/invoke/MethodHandles$Lookup
  #65 = Utf8               java/lang/invoke/MethodHandles$Lookup
  #66 = Class              #67            // java/lang/invoke/MethodHandles
  #67 = Utf8               java/lang/invoke/MethodHandles
  #68 = Utf8               Lookup
{
  public jvm.demo.move.average.LocalVariableTest();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 8: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Ljvm/demo/move/average/LocalVariableTest;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=3, locals=6, args_size=1
         0: new           #7                  // class jvm/demo/move/average/MovingAverage
         3: dup
         4: invokespecial #9                  // Method jvm/demo/move/average/MovingAverage."<init>":()V
         7: astore_1
         8: iconst_1
         9: istore_2
        10: iconst_2
        11: istore_3
        12: aload_1
        13: iload_2
        14: i2d
        15: invokevirtual #10                 // Method jvm/demo/move/average/MovingAverage.submit:(D)V
        18: aload_1
        19: iload_3
        20: i2d
        21: invokevirtual #10                 // Method jvm/demo/move/average/MovingAverage.submit:(D)V
        24: aload_1
        25: invokevirtual #14                 // Method jvm/demo/move/average/MovingAverage.getAvg:()D
        28: dstore        4
        30: getstatic     #18                 // Field java/lang/System.out:Ljava/io/PrintStream;
        33: dload         4
        35: invokedynamic #24,  0             // InvokeDynamic #0:makeConcatWithConstants:(D)Ljava/lang/String;
        40: invokevirtual #28                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        43: return
      LineNumberTable:
        line 11: 0
        line 12: 8
        line 13: 10
        line 14: 12
        line 15: 18
        line 16: 24
        line 18: 30
        line 19: 43
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      44     0  args   [Ljava/lang/String;
            8      36     1    ma   Ljvm/demo/move/average/MovingAverage;
           10      34     2  num1   I
           12      32     3  num2   I
           30      14     4   avg   D
}
SourceFile: "LocalVariableTest.java"
BootstrapMethods:
  0: #55 REF_invokeStatic java/lang/invoke/StringConcatFactory.makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/Ca
llSite;
    Method arguments:
      #61 avg = \u0001
InnerClasses:
  public static final #68= #64 of #66;    // Lookup=class java/lang/invoke/MethodHandles$Lookup of class java/lang/invoke/MethodHandles
```

## 启动一个jar,并且用 jps -lmv查看细节参数，执行jmap -heap <进程pid> 查看细节
以下是代码示例
```
C:\Git_Hub\JAVA-000\Week_02\src>java -jar gateway-server-0.0.1-SNAPSHOT.jar

C:\Git_Hub\JAVA-000>jps -lmv
8736  exit -Xms128m -Xmx1994m -XX:ReservedCodeCacheSize=240m -XX:+UseConcMarkSweepGC -XX:SoftRefLRUPolicyMSPerMB=50 -ea -XX:CICompilerCount=2 -Dsun.io.useCanonPrefixCache=false -Djdk.http.auth.tunneling.disabledSchemes="" -XX:+HeapD
umpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -Djdk.attach.allowAttachSelf=true -Dkotlinx.coroutines.debug=off -Djdk.module.illegalAccess.silent=true -Djb.vmOptionsFile=C:\Users\Administrator\AppData\Roaming\JetBrains\IdeaIC2
020.2\idea64.exe.vmoptions -Djava.library.path=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020\jbr\\bin;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2020\jbr\\bin\server -Didea.platform.prefix=Idea -Did
ea.jre.check=true -Dide.native.launcher=true -Didea.vendor.name=JetBrains -Didea.paths.selector=IdeaIC2020.2 -XX:ErrorFile=C:\Users\Administrator\java_error_in_idea_%p.log -XX:HeapDumpPath=C:\Users\Administrator\java_error_in_idea.h
prof
13380 gateway-server-0.0.1-SNAPSHOT.jar
9916 sun.tools.jps.Jps -lmv -Dapplication.home=C:\Program Files\Java\jdk1.8.0_231 -Xms8m

C:\Git_Hub\JAVA-000>jmap -heap 13380
Attaching to process ID 13380, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.231-b11

using thread-local object allocation.
Parallel GC with 8 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 0
   MaxHeapFreeRatio         = 100
   MaxHeapSize              = 4183818240 (3990.0MB)
   NewSize                  = 87031808 (83.0MB)
   MaxNewSize               = 1394606080 (1330.0MB)
   OldSize                  = 175112192 (167.0MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 198180864 (189.0MB)
   used     = 11836384 (11.288055419921875MB)
   free     = 186344480 (177.71194458007812MB)
   5.9725160951967595% used
From Space:
   capacity = 12582912 (12.0MB)
   used     = 0 (0.0MB)
   free     = 12582912 (12.0MB)
   0.0% used
To Space:
   capacity = 15204352 (14.5MB)
   used     = 0 (0.0MB)
   free     = 15204352 (14.5MB)
   0.0% used
PS Old Generation
   capacity = 167772160 (160.0MB)
   used     = 20278056 (19.338661193847656MB)
   free     = 147494104 (140.66133880615234MB)
   12.086663246154785% used

15760 interned Strings occupying 2094632 bytes.
```
