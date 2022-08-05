package com.luffy.safe.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * desc: 用tryLock设置超时时间避免死锁
 *
 * @author luffy
 * @since 2022/8/6 1:41
 */
public class TryLockDeadLock implements Runnable {
    int flag = 0;

    static Object lock1 = new Object();
    static Object lock2 = new Object();
    static Lock l1 = new ReentrantLock();
    static Lock l2 = new ReentrantLock();

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 100; i++) {
            try {
                if (flag == 0) {
                    if (l1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        System.out.println(name + "获取到锁1");
                        if (l2.tryLock(800, TimeUnit.MILLISECONDS)) {
                            System.out.println(name + " 所有锁获取成功");
                            l2.unlock();
                            l1.unlock();
                            break;
                        } else {
                            System.out.println(name + " 获取锁2失败，重试");
                            l1.unlock();
                        }

                    } else {
                        System.out.println(name + " 获取锁1失败，重试");
                    }
                } else {
                    if (l2.tryLock(800, TimeUnit.MILLISECONDS)) {
                        System.out.println(name + "获取到锁2");
                        if (l1.tryLock(800, TimeUnit.MILLISECONDS)) {
                            System.out.println(name + " 所有锁获取成功");
                            l1.unlock();
                            l2.unlock();
                            break;
                        } else {
                            System.out.println(name + " 获取锁1失败，重试");
                            l2.unlock();
                        }
                    } else {
                        System.out.println(name + " 获取锁2失败，重试");
                    }
                }
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TryLockDeadLock r1 = new TryLockDeadLock();
        TryLockDeadLock r2 = new TryLockDeadLock();
        r1.flag = 0;
        r2.flag = 1;
        Thread t1 = new Thread(r1, "线程1");
        Thread t2 = new Thread(r2, "线程2");
        t1.start();
        t2.start();
    }
}
