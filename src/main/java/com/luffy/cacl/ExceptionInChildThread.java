package com.luffy.cacl;

/**
 * desc: 子线程发生异常
 * 子线程发生异常不会影响主线程的执行，所以主线程可能会无法感知子线程发生的异常信息
 *
 * @author luffy
 * @since 2022/7/29 22:55
 */
public class ExceptionInChildThread implements Runnable {
    public static void main(String[] args) {
        new Thread(new ExceptionInChildThread()).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
