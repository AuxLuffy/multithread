package com.luffy.safe.multierror;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多进程错误分析
 * 对index共享变量执行++操作，分析并打印出错序号及线程
 *
 * @author sunzhangfei
 * @since 2022/8/1 2:23 下午
 */
public class MultiThreadErrorAnnaysis implements Runnable {
    public int index = 0;
    public boolean[] marked = new boolean[10000000];
    public AtomicInteger errorCount = new AtomicInteger();
    public AtomicInteger rightCount = new AtomicInteger();
    public CyclicBarrier b1 = new CyclicBarrier(2);//增加栅栏的目的是为了让所有线程执行完成操作才开始执行下一步操作
    public CyclicBarrier b2 = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException {
        MultiThreadErrorAnnaysis adder = new MultiThreadErrorAnnaysis();
        Thread t1 = new Thread(adder, "T1");
        Thread t2 = new Thread(adder, "T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终执行结果为：" + adder.index);
        System.out.println("errorCount: " + adder.errorCount.get());
        System.out.println("rightCount: " + adder.rightCount.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            try {
                b2.reset();
                b1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            try {
                b1.reset();
                b2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            synchronized (MultiThreadErrorAnnaysis.class) {
                if (marked[index] && marked[index - 1]) {//这里判断index-1是为了正常操作下肯定会出现奇数次或偶数次的问题，可以尝试把index-1的判断去掉即可观察现象
                    System.out.println(Thread.currentThread().getName() + "线程, 这一次已经加过了，出错运算的序号：" + i);
                    errorCount.incrementAndGet();
                } else {
                    rightCount.incrementAndGet();
                    marked[index] = true;
                }
            }
        }
    }
}
