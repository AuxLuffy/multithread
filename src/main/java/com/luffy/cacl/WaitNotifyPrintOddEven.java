package com.luffy.cacl;

/**
 * desc: wait notify方式交替打印奇偶数
 *
 * @author luffy
 * @since 2022/7/29 2:23
 */
public class WaitNotifyPrintOddEven {
    private static int count;
    private static Object lock = new Object();

    static Runnable printRunnable = new Runnable() {
        @Override
        public void run() {
            while (count <= 100) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
                    lock.notify();
                    if (count <= 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        lock.notify();
                    }
                }
            }
        }
    };

    public static void main(String[] args) {
        Thread even = new Thread(printRunnable, "偶数线程");
        Thread odd = new Thread(printRunnable, "奇数线程");
        even.start();
        odd.start();
    }
}
