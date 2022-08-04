package com.luffy.safe;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁的使用
 *
 * @author sunzhangfei
 * @since 2022/7/28 2:04 下午
 */
public class LockDemo implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new LockDemo());
        thread.start();
        try {
            Thread.sleep(5000);
            System.out.println("休眠5秒后去打断子线程执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        try {//用lock锁定的业务代码块必须有try...finally
            for (int i = 0; i < 10; i++) {//每过一秒钟打印一下当前时间
                System.out.println(new Date());
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("锁被中断了");
            e.printStackTrace();
        } finally {
            //必须在finally中释放锁，要不然会出现业务代码块中有异常中断了执行而导致此锁一直无法释放
            lock.unlock();
        }
    }

}
