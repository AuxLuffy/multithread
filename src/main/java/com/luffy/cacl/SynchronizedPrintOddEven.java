package com.luffy.cacl;

/**
 * desc: synchronized方式交替打印奇偶数
 *
 * @author luffy
 * @since 2022/7/29 2:14
 */
public class SynchronizedPrintOddEven {
    private static int count = 0;
    private static Object lock = new Object();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if ((count & 1) == 1) {
                            System.out.println(Thread.currentThread().getName() + ": " + count++);
                        }
                    }
                }
            }
        }, "奇数线程").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if ((count & 1) == 0) {
                            System.out.println(Thread.currentThread().getName() + ": " + count++);
                        }

                    }
                }
            }
        }, "偶数线程").start();
    }
}
