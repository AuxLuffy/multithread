package com.luffy.safe.multierror;

/**
 * 死锁代码演示
 * 出现的情况：线程中锁嵌套，另外一个线程访问其他线程的嵌套锁很有可能会出现死锁
 *
 * @author sunzhangfei
 * @since 2022/8/1 4:10 下午
 */
public class DeadLock implements Runnable {
    int flag = 1;

    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        DeadLock dead1 = new DeadLock();
        DeadLock dead2 = new DeadLock();
        dead1.flag = 1;
        dead2.flag = 0;
        new Thread(dead1, "T1").start();
        new Thread(dead2, "T2").start();
    }

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " getLock2");
                }
            }
        }
        if (flag == 0) {
            synchronized (lock2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + " getLock1");
                }
            }
        }
    }
}
