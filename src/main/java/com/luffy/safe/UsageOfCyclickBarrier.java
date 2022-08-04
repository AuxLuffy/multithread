package com.luffy.safe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier的使用
 * 循环栅栏，可循环利用的屏障
 * 作用：让所有线程等等完成后才会继续进行下一步操作
 * 使用场景：用于多线程计算，最后合并计算结果的情况
 * 和{@link java.util.concurrent.CountDownLatch}不同的时，前者是可循环使用的，后者是一次性的
 *
 * @author sunzhangfei
 * @since 2022/8/1 5:10 下午
 */
public class UsageOfCyclickBarrier implements Runnable {
    public static void main(String[] args) {
        int count = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "完成最后的任务");
            }
        });
        UsageOfCyclickBarrier runnable = new UsageOfCyclickBarrier(cyclicBarrier);
        for (int i = 0; i < count; i++) {
            new Thread(runnable).start();
        }
    }

    public UsageOfCyclickBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    CyclicBarrier barrier;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " 到达栅栏A");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " 冲破栅栏A");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " 到达栅栏B");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " 冲破栅栏B");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
