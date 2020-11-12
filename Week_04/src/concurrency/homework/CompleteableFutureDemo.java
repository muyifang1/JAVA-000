package concurrency.homework;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompleteableFuture
 */
public class CompleteableFutureDemo {

    private int sum(int num) {
        return fibo(num);
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int result = 0;
        long start = System.currentTimeMillis();
        System.out.println("开始计时：start = " + start + " ms");

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        CompleteableFutureDemo demo = new CompleteableFutureDemo();

        result = CompletableFuture.supplyAsync(() -> demo.sum(36)).join();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        System.exit(8);
    }
}
