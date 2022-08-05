package com.luffy.safe.deadlock;

/**
 * desc: interrupt接口是否会打断锁
 *
 * @author luffy
 * @since 2022/8/6 1:31
 */
public class InterruptSynchronized {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (InterruptSynchronized.class) {
                    while (true) {
                        try {
                            Thread.sleep(100_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
//                System.out.println("释放了锁");
            }
        });
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

}
