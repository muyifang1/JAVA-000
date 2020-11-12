package concurrency.homework;

import java.util.concurrent.*;

public class ExecutorsFutureDemo {

    private volatile Integer value = null;
    private Semaphore semaphore;

    private void sum(int num) {
        value = fibo(num);
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public Integer getResult() {
        return value;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int result = 0;
        long start = System.currentTimeMillis();
        System.out.println("开始计时：start = " + start + " ms");

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorsFutureDemo demo = new ExecutorsFutureDemo();

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = (Future<Integer>) executorService.submit(() -> {
            demo.sum(36);
            return demo.getResult();
        });

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + future.get());
        executorService.shutdown();
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        System.exit(8);
    }
}
