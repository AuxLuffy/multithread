package com.luffy.cacl.mutithread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * desc: 多线程发生的错误
 *
 * @author luffy
 * @since 2022/7/30 1:23
 */
public class MultiThreadError implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        MultiThreadError task = new MultiThreadError();
        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终结果：" + task.index);
        System.out.println("出错次数：" + task.errorCount.get());
        System.out.println("正确次数：" + task.rightCount.get());
    }

    volatile int index = 0;
    boolean[] marks = new boolean[1000000];
    AtomicInteger errorCount = new AtomicInteger();
    AtomicInteger rightCount = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
            synchronized (this) {
                if (marks[index]) {
                    errorCount.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "线程的第 " + i + " 次已经加过了");
                } else {
                    marks[index] = true;
//                rightCount.incrementAndGet();
                }
            }

        }
    }
}
