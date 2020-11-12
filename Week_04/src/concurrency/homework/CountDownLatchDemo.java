package concurrency.homework;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 初始化一个 count数
 * 主线程 await() 一直等到 countDown 数量为零
 * 子线程 countDown() 使count数递减
 */
public class CountDownLatchDemo {

    private volatile Integer value = null;
    private CountDownLatch latch;

    private void sum(int num) {
        value = fibo(num);
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    /**
     * 设置CountDownLatch
     *
     * @param latch
     */
    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public int getResult() {
        return value;
    }

    public static void main(String[] args) {

        int result = 0;
        long start = System.currentTimeMillis();
        System.out.println("开始计时：start = " + start + " ms");

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        CountDownLatchDemo demo = new CountDownLatchDemo();
        demo.setLatch(new CountDownLatch(1));

        Thread thread = new Thread(() -> {
            demo.sum(36);
            // 线程中调用countDown() count计数减1
            demo.latch.countDown();
        });
        thread.start();

        // 主线程等待所有子线程结束（直到count数为0时）
        try {
            demo.latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = demo.getResult();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        System.exit(8);
    }

}
