package com.luffy.safe;

/**
 * join的使用
 *
 * @author sunzhangfei
 * @since 2022/7/29 11:40 上午
 */
public class JoinDemo implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "线程执行开始");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "线程执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            synchronized (this) {
//                System.out.println(Thread.currentThread().getName() + "线程notifyAll()");
//                this.notifyAll();
//            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new JoinDemo(), "T1");
        Thread t2 = new Thread(new JoinDemo(), "T2");
        System.out.println("子线程start");
        t1.start();
        t2.start();
//        synchronized (t1) {
//            t1.wait();
//        }
//        synchronized (t2) {
//            t2.wait();
//        }
        t2.join();
        t1.join();
        System.out.println("所有子线程执行完毕");
    }
}
