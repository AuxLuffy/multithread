package com.luffy.safe.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 演示重排序的现象
 * “直到达到某个条件才停止”，测试小概率事件
 *
 * @author sunzhangfei
 * @since 2022/8/1 8:06 下午
 */
public class OutOfOrderExecution {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int time = 0;
        for (; ; ) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(2);
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    latch.countDown();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a = 1;
                    x = b;
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    latch.countDown();
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b = 1;
                    y = a;
                }
            });
            t2.start();
            t1.start();
            t1.join();
            t2.join();
            time++;
            System.out.println("经历" + time + "次后，x=" + x + ", y=" + y);
            if (x == 0 && y == 0) {
                break;
            }
        }

    }
}
