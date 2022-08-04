package com.luffy.cacl;

/**
 * desc:
 *
 * @author luffy
 * @since 2022/7/27 22:13
 */
public class RightWayStopThreadInProd2 implements Runnable {

    public static void main(String[] args) {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 线程中尝试打断线程：" + thread.getName());
        thread.interrupt();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始执行");
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " thread Interrupted, 程序运行结束");
                break;
            }
            reInterrupt();
        }
    }

    private void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();//这里重新把中断抛出
        }
    }
}
