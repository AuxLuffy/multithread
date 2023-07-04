package com.luffy.cacl;

/**
 * desc: 展示wait和notify的用法
 *
 * @author luffy
 * @since 2022/7/28 23:53
 */
public class Wait {
    public static Object lock = new Object();

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("线程" + Thread.currentThread().getName() + "开始执行了");
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "调用了wait()");
                    lock.wait();
//                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "获得了锁");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("线程" + Thread.currentThread().getName() + "开始执行了");
                lock.notify();//notify如果不在它本身对象的锁定代码块中执行的话就会报错current thread is not owner
                System.out.println("线程" + Thread.currentThread().getName() + "调用了notify()");
            }
        }
    }

    public static void main(String[] args) {
//        try {
//            lock.wait();//wait()如果不在sychronize锁的代码块中执行就会报current thread is not owner的错误
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Thread1 t1 = new Thread1();
        t1.start();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("调用了interrupt");
//        t1.interrupt();//interrupt也可以释放锁，打断wait状态
        Thread2 t2 = new Thread2();
        t2.start();
    }
}
