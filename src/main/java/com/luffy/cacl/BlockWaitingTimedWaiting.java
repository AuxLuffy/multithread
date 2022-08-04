package com.luffy.cacl;

/**
 * desc: 展示Blocked, Waiting, Timed Waiting状态
 *
 * @author luffy
 * @since 2022/7/28 23:00
 */
public class BlockWaitingTimedWaiting implements Runnable {

    public static void main(String[] args) {
        BlockWaitingTimedWaiting runnable = new BlockWaitingTimedWaiting();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程1 " + thread1.getState());
        System.out.println("线程2 " + thread2.getState());
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程1 " + thread1.getState());
        System.out.println("线程2 " + thread2.getState());
    }

    @Override
    public void run() {
        syn();
    }

    private synchronized void syn() {
        try {
            Thread.sleep(1000);
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
