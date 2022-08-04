package com.luffy.safe;

import java.util.concurrent.CountDownLatch;

/**
 * 通过CountDownLatch及其它方式实现join线程
 *
 * @author sunzhangfei
 * @since 2022/7/29 4:08 下午
 */
public class JoinByCounDownLatch {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("主线程开始了");
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("子线程开始了");
                        Thread.sleep(5000);
                        System.out.println("子线程结束了");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
//            thread.join();
//            Thread.yield();
            thread.getId();
            latch.await();
            System.out.println("主线程唤醒了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
