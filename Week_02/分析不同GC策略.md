#使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。#

## 并行GC ##
#### javac -encoding UTF-8 指定UTF-8字符集编译java文件 ####
`PS C:\Git_Hub\JAVA-000\Week_02\src> javac -encoding UTF-8 .\GCLogAnalysis.java`

##### -XX:+PrintGCDetails 参数运行同时打印GC日志详情 #####
注意：因为是java8 默认使用了并行GC -XX:+UseParallelGC，这次执行并没有指定堆内存大小应该是默认4分之一机器内存(16G/4=4G)
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 64512K->10236K(74752K)] 64512K->26418K(245760K), 0.0067054 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 74383K->10232K(139264K)] 90565K->47811K(310272K), 0.0070368 secs] [Times: user=0.05 sys=0.08, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 139256K->10235K(139264K)] 176835K->91252K(310272K), 0.0104223 secs] [Times: user=0.03 sys=0.09, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 138899K->10224K(268288K)] 219916K->134600K(439296K), 0.0097313 secs] [Times: user=0.03 sys=0.09, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 10224K->0K(268288K)] [ParOldGen: 124376K->121126K(258048K)] 134600K->121126K(526336K), [Metaspace: 2703K->2703K(1056768K)], 0.0206578 secs] [Times: user=0.13 sys=0.00, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 258048K->10232K(268288K)] 379174K->202070K(526336K), 0.0140797 secs] [Times: user=0.11 sys=0.00, real=0.02 secs]
[Full GC (Ergonomics) [PSYoungGen: 10232K->0K(268288K)] [ParOldGen: 191837K->173906K(370176K)] 202070K->173906K(638464K), [Metaspace: 2703K->2703K(1056768K)], 0.0261824 secs] [Times: user=0.16 sys=0.00, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 258048K->87029K(485888K)] 431954K->262690K(856064K), 0.0137242 secs] [Times: user=0.03 sys=0.09, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 485877K->102383K(513536K)] 661538K->352732K(883712K), 0.0268991 secs] [Times: user=0.03 sys=0.11, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 513519K->157181K(742400K)] 763868K->431981K(1112576K), 0.0270939 secs] [Times: user=0.05 sys=0.20, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 742397K->194554K(779776K)] 1017197K->536126K(1149952K), 0.0474450 secs] [Times: user=0.06 sys=0.31, real=0.05 secs]
[Full GC (Ergonomics) [PSYoungGen: 194554K->0K(779776K)] [ParOldGen: 341572K->344900K(586240K)] 536126K->344900K(1366016K), [Metaspace: 2703K->2703K(1056768K)], 0.0470621 secs] [Times: user=0.34 sys=0.00, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 585216K->157831K(1056768K)] 930116K->502732K(1643008K), 0.0232959 secs] [Times: user=0.03 sys=0.09, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 961159K->194251K(1061888K)] 1306060K->615928K(1648128K), 0.0332812 secs] [Times: user=0.11 sys=0.14, real=0.03 secs]
执行结束!共生成对象次数:15840
Heap
 PSYoungGen      total 1061888K, used 719392K [0x000000076ce00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 803328K, 65% used [0x000000076ce00000,0x000000078ced5628,0x000000079de80000)
  from space 258560K, 75% used [0x000000079de80000,0x00000007a9c32c28,0x00000007adb00000)
  to   space 274944K, 0% used [0x00000007af380000,0x00000007af380000,0x00000007c0000000)
 ParOldGen       total 586240K, used 421677K [0x00000006c6a00000, 0x00000006ea680000, 0x000000076ce00000)
  object space 586240K, 71% used [0x00000006c6a00000,0x00000006e05cb6a0,0x00000006ea680000)
 Metaspace       used 2709K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 291K, capacity 386K, committed 512K, reserved 1048576K
```
###### 分析一次GC行为 ######
`[GC (Allocation Failure) [PSYoungGen: 64512K->10236K(74752K)] 64512K->26418K(245760K), 0.0067054 secs]`
> 第一次 MinorGC 年轻代堆内存从 64512K 降低到 10236K (年轻代总内存74752K) 老年代堆内存从 64512K 降低到 26418K(老年代总共245760K) 本次GC执行时间 0.0067054 secs
> Allocation Failure 表示GC原因，分配空间不足
> Full GC (Ergonomics)
> [Times: user=0.06 sys=0.31, real=0.05 secs]
  这个是CPU时间 用户线程时间 0.06, 系统线程时间 0.31，真正线程时间 0.05 secs

###### 分析 Heap 堆内存 ######
+ 1. PSYoungGen年轻代 total 1061888K, used 719392K
    * eden space 803328K, 65% used
    * from space 258560K, 75% used
    * to   space 274944K, 0% used
+ 2. ParOldGen 老年代 total 586240K, used 421677K
    * object space 586240K, 71% used
+ 3. Metaspace       used 2709K, capacity 4486K, committed 4864K, reserved 1056768K
     class space    used 291K, capacity 386K, committed 512K, reserved 1048576K
>>> 元数据区共使用了 2709K, 容量 4486K， jvm保证可用大小是 4864K,保留空间 1056768K

##### -Xloggc:gcdemo.parallel.%p-%t.log 输出一个GClog文件命名方式中有%p(pid进程id)和%t(启动时间戳) #####
##### -XX:+PrintGCDateStamps log文件中记录了每一次GC时间戳 #####
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xloggc:gcdemo.parallel.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:15054
```
> 上述命令执行完，路径下生成了一个文件 gcdemo.parallel.pid13016-2020-10-27_22-31-44.log

##### -Xmx128m 分别指定堆内存为 128M 512M 1G 2G 再次执行并生成log文件对比 #####
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xmx128m -Xloggc:gc128m.parallel.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:46)
        at GCLogAnalysis.main(GCLogAnalysis.java:29)
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xmx512m -Xloggc:gc512m.parallel.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:9051
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xmx1g -Xloggc:gc1g.parallel.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:13884
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xmx2g -Xloggc:gc2g.parallel.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:14723
```
##### 分析上述结果 #####
|堆内存大小|共生成对象次数|Log中FullGC次数|
|128M|OutOfMemoryError: Java heap space|异常前20次FullGC|
|512M|执行结束!共生成对象次数:9051|共执行13次FullGC,结束前连续7次FullGC|
|1G|执行结束!共生成对象次数:13884|共执行5次FullGC|
|2G|执行结束!共生成对象次数:14723|共执行4次FullGC|
|本机默认(4G)|执行结束!共生成对象次数:15840|共执行3次FullGC|
##### 总结 #####
并行GC，堆内存越大，效率越高，FullGC次数越少

## 串行GC ##
##### -Xmx4g ‐XX:+UseSerialGC -XX:+PrintGCDetails 参数运行同时打印GC日志详情 #####
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xmx4g -XX:+UseSerialGC -Xloggc:gc4g.serial.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:11257
```

## CMS GC ##
##### -Xmx4g ‐XX:+UseConcMarkSweepGC -XX:+PrintGCDetails 参数运行同时打印GC日志详情 #####
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -Xmx4g -XX:+UseConcMarkSweepGC -Xloggc:gc4g.cms.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:12082
```
总结：从【gc4g.cms.pid12296-2020-10-27_23-52-51.log】文件中可以看到，没有出现FullGC。CMS优点在于增加吞吐量。

## G1 GC ##
##### ‐XX:+UseG1GC -XX:+PrintGCDetails 参数运行同时打印GC日志详情 #####
注意：G1GC 不指定堆内存大小！因为G1的回收方式是小批量划定区块(region)进行，可能出现一次普通GC中既有年轻代又有老年代，也可能某块区域一会是老年代，一会是年轻代。
```
PS C:\Git_Hub\JAVA-000\Week_02\src> java -XX:+UseG1GC -Xloggc:gc.g1.%p-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:9182
```
