package com.luffy.cacl;

import java.util.concurrent.CountDownLatch;

/**
 * desc:
 *
 * @author luffy
 * @since 2022/7/24 20:48
 */
public class CacheTest {

    public static final long COUNT = 1_000_000_000L;


    private static class T {
        private long p1, p2, p3, p4, p5, p6, p7, p8;
        public long x = 0L;
        private long p11, p12, p13, p14, p15,p16,p17,p18;

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                arr[0].x = i;
            }
            latch.countDown();
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < COUNT; i++) {
                    arr[1].x = i;
                }
                latch.countDown();
            }
        });
        long start = System.nanoTime();
        t1.start();
        t2.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((System.nanoTime() - start) / 1000);
    }
}
