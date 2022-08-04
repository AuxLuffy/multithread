package com.luffy.safe;

/**
 * 子线程join被中断情况演示
 *
 * @author sunzhangfei
 * @since 2022/7/29 11:45 上午
 */
public class JoinInterrupt {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入了子线程");
                try {
                    System.out.println("打断主线程");
                    mainThread.interrupt();
                    Thread.sleep(5000);
                    System.out.println("子线程运行结束");
                } catch (InterruptedException e) {
                    System.out.println("子线程被中断了");
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println("主线程被中断了");
            e.printStackTrace();
            //当主线程被中断时最好把中断传递给所有子线程，否则子线程会继续运行
            t1.interrupt();
        }
        System.out.println("所有子线程执行完了");
    }
}
