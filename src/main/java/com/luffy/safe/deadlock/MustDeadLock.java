package com.luffy.safe.deadlock;

/**
 * 必定发生死锁的情况
 *
 * @author sunzhangfei
 * @since 2022/8/5 3:59 下午
 */
public class MustDeadLock {
    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (lock1) {
                    System.out.println(name + "线程获取锁1开始执行");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + "线程开始去获取锁2");
                    synchronized (lock2) {
                        System.out.println(name + "线程获取到锁2");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (lock2) {
                    System.out.println(name + "线程获取锁2开始执行");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + "线程开始去获取锁1");
                    synchronized (lock1) {
                        System.out.println(name + "线程获取到锁1");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
