package concurrency.homework;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierDemo
 * CyclicBarrier初始化时指定parties计数 和一个回调方法
 * 子线程每次await() 调用 partires计数增加1
 * 当 parties等于初始化参数时，回调方法执行。
 */
public class CyclicBarrierDemo {

    private volatile Integer value = null;
    private CyclicBarrier barrier;

    private void sum(int num) {
        value = fibo(num);
    }

    private int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public int getResult() {
        return value;
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        System.out.println("开始计时：start = " + start + " ms");

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> {
            // 函数回调，当 parties数等于1时执行(初始化第一个参数指定parties)
            int result = 0;
            
            result = demo.getResult();

            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

            // 然后退出main线程
            System.exit(8);
        });
        demo.setBarrier(cyclicBarrier);

        Thread thread = new Thread(() -> {

            demo.sum(36);
            try {
                // 子线程await()使parties数增加1
                demo.barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

}
